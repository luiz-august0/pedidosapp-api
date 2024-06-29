package com.pedidosapp.api.service;

import com.pedidosapp.api.config.multitenancy.TenantContext;
import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.infrastructure.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.infrastructure.exceptions.enums.EnumGenericsException;
import com.pedidosapp.api.infrastructure.exceptions.enums.EnumResourceNotFoundException;
import com.pedidosapp.api.infrastructure.exceptions.enums.EnumUnauthorizedException;
import com.pedidosapp.api.model.beans.TokenBean;
import com.pedidosapp.api.model.dtos.EmployeeDTO;
import com.pedidosapp.api.model.entities.User;
import com.pedidosapp.api.model.records.AuthenticationRecord;
import com.pedidosapp.api.repository.UserRepository;
import com.pedidosapp.api.utils.StringUtil;
import com.pedidosapp.api.utils.TokenUtil;
import com.pedidosapp.api.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class AuthenticationService {

    @Autowired
    private HttpServletRequest request;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final UserRepository userRepository;

    private final UserService userService;

    public ResponseEntity<TokenBean> login(AuthenticationRecord authenticationRecord) {
        if (StringUtil.isNullOrEmpty(TenantContext.getCurrentTenant())
                || TenantContext.getCurrentTenant().equals(TenantContext.DEFAULT_TENANT)) {
            throw new ApplicationGenericsException(EnumGenericsException.LOGIN_WITHOUT_TENANT);
        }

        if (StringUtil.isNullOrEmpty(userRepository.findSchemaByName(TenantContext.getCurrentTenant()))) {
            throw new ApplicationGenericsException(EnumGenericsException.INVALID_TENANT);
        }

        var loginPassword = new UsernamePasswordAuthenticationToken(authenticationRecord.login(), authenticationRecord.password());
        EnumUnauthorizedException userInactiveEnum = EnumUnauthorizedException.USER_INACTIVE;

        try {
            Authentication session = authenticationManager.authenticate(loginPassword);
            User user = (User) session.getPrincipal();

            if (!user.getActive()) throw new ApplicationGenericsException(userInactiveEnum);

            String accessToken = tokenService.generateToken(user);
            String refreshToken = tokenService.generateRefreshToken(user);

            TokenBean tokenBean = makeTokenBeanFromUser(user, accessToken, refreshToken);

            return ResponseEntity.ok(tokenBean);
        } catch (RuntimeException e) {
            if (e.getClass() == BadCredentialsException.class) {
                throw new ApplicationGenericsException(EnumUnauthorizedException.WRONG_CREDENTIALS);
            } else {
                if (e.getMessage().equals(userInactiveEnum.getMessage())) {
                    throw new ApplicationGenericsException(userInactiveEnum);
                } else {
                    throw new ApplicationGenericsException(e.getMessage());
                }
            }
        }
    }

    public ResponseEntity<TokenBean> refreshToken(TokenBean tokenBeanRequest) {
        User user;
        String[] subject = new String(
                Base64.getDecoder().decode(tokenService.validateToken(tokenBeanRequest.getRefreshToken()).getBytes())
        ).split("-");
        Integer userId = Integer.parseInt(subject[0]);
        String schema = subject[1];

        TenantContext.setCurrentTenant(schema);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new ApplicationGenericsException(
                    EnumResourceNotFoundException.RESOURCE_NOT_FOUND, new User().getPortugueseClassName(), userId
            );
        }

        String accessToken = tokenService.generateToken(user);
        String newRefreshToken = tokenService.generateRefreshToken(user);

        TokenBean tokenBean = makeTokenBeanFromUser(user, accessToken, newRefreshToken);

        return ResponseEntity.ok(tokenBean);
    }

    public ResponseEntity<TokenBean> getSession() {
        User user = userService.findAndValidate(userService.getUserByContext().getId());

        if (!user.getActive()) throw new ApplicationGenericsException(EnumUnauthorizedException.USER_INACTIVE);

        TokenBean tokenBean = makeTokenBeanFromUser(user, TokenUtil.getTokenFromRequest(request), tokenService.generateRefreshToken(user));

        return ResponseEntity.ok(tokenBean);
    }

    private TokenBean makeTokenBeanFromUser(User user, String accessToken, String refreshToken) {
        TokenBean tokenBean = new TokenBean();
        tokenBean.setUserId(user.getId());
        tokenBean.setLogin(user.getLogin());
        tokenBean.setRole(user.getRole());
        tokenBean.setPhoto(user.getPhoto());

        if (Utils.isNotEmpty(user.getEmployee())) {
            tokenBean.setEmployee(Converter.convertEntityToDTO(user.getEmployee(), EmployeeDTO.class));
        }

        tokenBean.setAccessToken(accessToken);
        tokenBean.setRefreshToken(refreshToken);

        return tokenBean;
    }
}
