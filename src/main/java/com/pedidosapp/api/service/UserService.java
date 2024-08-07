package com.pedidosapp.api.service;

import com.pedidosapp.api.external.s3.S3StorageService;
import com.pedidosapp.api.infrastructure.converter.Converter;
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
public class UserService extends AbstractService<UserRepository, User, UserDTO, UserValidator> {
    private final UserRepository userRepository;

    private final UserValidator validator;

    private final S3StorageService s3StorageService;

    private final MultipartBeanValidator multipartBeanValidator;

    public UserService(UserRepository userRepository, S3StorageService s3StorageService) {
        super(userRepository, new User(), new UserDTO(), new UserValidator(userRepository));
        this.userRepository = userRepository;
        this.validator = new UserValidator(userRepository);
        this.s3StorageService = s3StorageService;
        this.multipartBeanValidator = new MultipartBeanValidator();
    }

    @Override
    @Transactional
    public ResponseEntity<UserDTO> insert(User user) {
        user.setRole(EnumUserRole.ADMIN);

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        resolverUserPhoto(user);

        validator.validate(user);

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(Converter.convertEntityToDTO(user, UserDTO.class));
    }

    @Override
    @Transactional
    public ResponseEntity<UserDTO> update(Integer id, User user) {
        User userOld = super.findAndValidate(id);

        user.setId(userOld.getId());
        user.setRole(userOld.getRole());
        user.setActive(Utils.nvl(user.getActive(), Boolean.TRUE));

        Employee oldEmployee = userOld.getEmployee();

        if (Utils.isNotEmpty(oldEmployee)) {
            Employee employee = new Employee();

            employee.setId(oldEmployee.getId());
            employee.setActive(user.getActive());
            employee.setCpf(oldEmployee.getCpf());
            employee.setContact(oldEmployee.getContact());
            employee.setEmail(oldEmployee.getEmail());
            employee.setName(oldEmployee.getName());
            employee.setUser(user);

            user.setEmployee(employee);
        }

        if (userOld.getLogin().equals("admin")) {
            user.setLogin("admin");
        }

        if (StringUtil.isNotNullOrEmpty(user.getPassword())) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        } else {
            user.setPassword(userOld.getPassword());
        }

        resolverUserPhoto(user);

        validator.validate(user);

        userRepository.save(user);

        return ResponseEntity.ok().body(Converter.convertEntityToDTO(user, UserDTO.class));
    }

    @Transactional
    public ResponseEntity<UserDTO> updateContextUser(User user) {
        user.setActive(Boolean.TRUE);

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