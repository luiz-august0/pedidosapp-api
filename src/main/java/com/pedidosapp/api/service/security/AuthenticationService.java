package com.pedidosapp.api.service.security;

import com.pedidosapp.api.config.multitenancy.TenantContext;
import com.pedidosapp.api.converter.Converter;
import com.pedidosapp.api.model.beans.TokenBean;
import com.pedidosapp.api.model.dtos.AuthenticationDTO;
import com.pedidosapp.api.model.dtos.EmployeeDTO;
import com.pedidosapp.api.model.entities.User;
import com.pedidosapp.api.repository.UserRepository;
import com.pedidosapp.api.service.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.service.exceptions.enums.EnumResourceNotFoundException;
import com.pedidosapp.api.service.exceptions.enums.EnumUnauthorizedException;
import com.pedidosapp.api.utils.Utils;
import lombok.RequiredArgsConstructor;
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

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final UserRepository userRepository;

    public ResponseEntity<TokenBean> login(AuthenticationDTO authenticationDTO) {
        var loginPassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        EnumUnauthorizedException userInactiveEnum = EnumUnauthorizedException.USER_INACTIVE;

        try {
            Authentication session = authenticationManager.authenticate(loginPassword);
            User user = (User) session.getPrincipal();

            if (!user.getActive()) throw new ApplicationGenericsException(userInactiveEnum);

            String accessToken = tokenService.generateToken(user);
            String refreshToken = tokenService.generateRefreshToken(user);

            TokenBean tokenBean = new TokenBean();
            tokenBean.setUserId(user.getId());
            tokenBean.setLogin(user.getLogin());
            tokenBean.setRole(user.getRole());

            if (Utils.isNotEmpty(user.getEmployee())) {
                tokenBean.setEmployee(Converter.convertEntityToDTO(user.getEmployee(), EmployeeDTO.class));
            }

            tokenBean.setAccessToken(accessToken);
            tokenBean.setRefreshToken(refreshToken);

            return ResponseEntity.ok(tokenBean);
        } catch (RuntimeException e) {
            if (e.getClass() == BadCredentialsException.class) {
                throw new ApplicationGenericsException(EnumUnauthorizedException.WRONG_CREDENTIALS);
            }
            else {
                if (e.getMessage().equals(userInactiveEnum.getMessage())) {
                    throw new ApplicationGenericsException(userInactiveEnum);
                } else {
                    throw new ApplicationGenericsException(e.getMessage());
                }
            }
        }
    }

    public ResponseEntity<TokenBean> refreshToken(TokenBean tokenBeanRequest) {
        User user = null;
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

        var accessToken = tokenService.generateToken(user);
        var newRefreshToken = tokenService.generateRefreshToken(user);

        TokenBean tokenBean = new TokenBean();

        tokenBean.setUserId(user.getId());
        tokenBean.setLogin(user.getLogin());
        tokenBean.setRole(user.getRole());

        if (Utils.isNotEmpty(user.getEmployee())) {
            tokenBean.setEmployee(Converter.convertEntityToDTO(user.getEmployee(), EmployeeDTO.class));
        }

        tokenBean.setAccessToken(accessToken);
        tokenBean.setRefreshToken(newRefreshToken);

        return ResponseEntity.ok(tokenBean);
    }
}
