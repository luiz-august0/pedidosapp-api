package com.pedidosapp.api.service;

import com.pedidosapp.api.external.s3.S3StorageService;
import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.infrastructure.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.infrastructure.exceptions.enums.EnumUnauthorizedException;
import com.pedidosapp.api.model.beans.EmployeeBean;
import com.pedidosapp.api.model.dtos.UserDTO;
import com.pedidosapp.api.model.entities.Employee;
import com.pedidosapp.api.model.entities.User;
import com.pedidosapp.api.model.enums.EnumUserRole;
import com.pedidosapp.api.repository.UserRepository;
import com.pedidosapp.api.utils.FileUtil;
import com.pedidosapp.api.utils.StringUtil;
import com.pedidosapp.api.utils.Utils;
import com.pedidosapp.api.validators.MultipartBeanValidator;
import com.pedidosapp.api.validators.UserValidator;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<UserRepository, User, UserDTO, UserValidator>  {
    private final UserRepository userRepository;

    private final UserValidator validator;

    private final EmployeeService employeeService;

    private final S3StorageService s3StorageService;

    private final MultipartBeanValidator multipartBeanValidator;

    public UserService(UserRepository userRepository, EmployeeService employeeService, S3StorageService s3StorageService) {
        super(userRepository, new User(), new UserDTO(), new UserValidator(userRepository));
        this.userRepository = userRepository;
        this.validator = new UserValidator(userRepository);
        this.employeeService = employeeService;
        this.s3StorageService = s3StorageService;
        this.multipartBeanValidator = new MultipartBeanValidator();
    }

    @Override
    @Transactional
    public ResponseEntity<UserDTO> insert(User user) {
        user.setRole(EnumUserRole.ADMIN);

        validator.validate(user);

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        resolverUserPhoto(user);

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(Converter.convertEntityToDTO(user, UserDTO.class));
    }

    @Override
    @Transactional
    public ResponseEntity<UserDTO> update(Integer id, User user) {
        User userOld = super.findAndValidate(id);

        Employee employee = userOld.getEmployee();

        user.setEmployee(Utils.nvl(employee, null));
        user.setId(userOld.getId());
        user.setRole(userOld.getRole());
        user.setActive(Boolean.TRUE);

        if (userOld.getLogin().equals("admin")) {
            user.setLogin("admin");
        }

        if (StringUtil.isNotNullOrEmpty(user.getPassword())) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        } else {
            user.setPassword(userOld.getPassword());
        }

        validator.validate(user);

        resolverUserPhoto(user);

        userRepository.save(user);

        return ResponseEntity.ok().body(Converter.convertEntityToDTO(user, UserDTO.class));
    }

    @Override
    @Transactional
    public ResponseEntity<UserDTO> activateInactivate(Integer id, Boolean active) {
        User user = super.findAndValidate(id);
        user.setActive(active);

        if (user.getLogin().equals("admin") && !active) {
            throw new ApplicationGenericsException(EnumUnauthorizedException.ADMIN_CANNOT_BE_DEACTIVATED);
        }

        Employee employee = user.getEmployee();

        if (Utils.isNotEmpty(employee)) {
            employeeService.activateInactivate(employee.getId(), active);
        } else {
            userRepository.save(user);
        }

        return ResponseEntity.ok().body(Converter.convertEntityToDTO(user, UserDTO.class));
    }

    @Transactional
    public ResponseEntity<UserDTO> updateContextUser(User user) {
        return this.update(getUserByContext().getId(), user);
    }

    private void resolverUserPhoto(User user) {
        if (StringUtil.isNotNullOrEmpty(user.getPhoto())) {
            s3StorageService.delete(FileUtil.getFilenameFromS3Url(user.getPhoto()));
        }

        if (Utils.isNotEmpty(user.getPhotoMultipart())) {
            multipartBeanValidator.validate(user.getPhotoMultipart());

            user.setPhoto(s3StorageService.upload(user.getPhotoMultipart(), true));
        } else {
            user.setPhoto(null);
        }
    }

}