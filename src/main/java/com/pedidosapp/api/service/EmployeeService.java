package com.pedidosapp.api.service;

import com.pedidosapp.api.external.s3.S3StorageService;
import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.infrastructure.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.infrastructure.exceptions.enums.EnumUnauthorizedException;
import com.pedidosapp.api.model.beans.EmployeeBean;
import com.pedidosapp.api.model.dtos.EmployeeDTO;
import com.pedidosapp.api.model.entities.Employee;
import com.pedidosapp.api.model.entities.User;
import com.pedidosapp.api.model.enums.EnumUserRole;
import com.pedidosapp.api.repository.EmployeeRepository;
import com.pedidosapp.api.repository.UserRepository;
import com.pedidosapp.api.utils.FileUtil;
import com.pedidosapp.api.utils.StringUtil;
import com.pedidosapp.api.utils.Utils;
import com.pedidosapp.api.validators.EmployeeValidator;
import com.pedidosapp.api.validators.MultipartBeanValidator;
import com.pedidosapp.api.validators.UserValidator;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends AbstractService<EmployeeRepository, Employee, EmployeeDTO, EmployeeValidator> {

    private final EmployeeRepository employeeRepository;

    private final UserRepository userRepository;

    private final EmployeeValidator employeeValidator;

    private final MultipartBeanValidator multipartBeanValidator;

    private final UserValidator userValidator;

    private final S3StorageService s3StorageService;

    EmployeeService(EmployeeRepository employeeRepository, UserRepository userRepository, S3StorageService s3StorageService) {
        super(employeeRepository, new Employee(), new EmployeeDTO(), new EmployeeValidator(employeeRepository));
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.employeeValidator = new EmployeeValidator(employeeRepository);
        this.userValidator = new UserValidator(userRepository);
        this.multipartBeanValidator = new MultipartBeanValidator();
        this.s3StorageService = s3StorageService;
    }

    @Transactional
    public ResponseEntity<EmployeeDTO> insert(EmployeeBean bean) {
        if (userRepository.findByLogin(bean.getLogin()) != null)
            throw new ApplicationGenericsException(EnumUnauthorizedException.USER_ALREADY_REGISTERED);

        String encryptedPassword = new BCryptPasswordEncoder().encode(bean.getPassword());
        User user = new User(bean.getLogin(), encryptedPassword, EnumUserRole.EMPLOYEE);

        user.setActive(bean.getActive());

        resolverUserPhoto(user, bean);

        userValidator.validate(user);

        userRepository.save(user);

        Employee employee = new Employee(null, bean.getName(), bean.getEmail(), bean.getCpf(), bean.getContact(), user, bean.getActive());

        employeeValidator.validate(employee);

        employeeRepository.save(employee);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<EmployeeDTO> update(Integer id, EmployeeBean bean) {
        Employee employee = super.findAndValidate(id);
        User user = employee.getUser();

        if ((bean.getLogin() != null) && (!bean.getLogin().equals(user.getLogin()))) {
            if (userRepository.findByLogin(bean.getLogin()) != null)
                throw new ApplicationGenericsException(EnumUnauthorizedException.USER_ALREADY_REGISTERED);

            user.setLogin(bean.getLogin());
        }

        if (bean.getPassword() != null) {
            user.setPassword(new BCryptPasswordEncoder().encode(bean.getPassword()));
        }

        user.setActive(bean.getActive());

        resolverUserPhoto(user, bean);

        employee.setName(bean.getName());
        employee.setEmail(bean.getEmail());
        employee.setCpf(bean.getCpf());
        employee.setContact(bean.getContact());
        employee.setUser(user);
        employee.setActive(bean.getActive());

        employeeValidator.validate(employee);

        userValidator.validate(user);

        employeeRepository.save(employee);

        userRepository.save(user);

        return ResponseEntity.ok().body(Converter.convertEntityToDTO(employee, EmployeeDTO.class));
    }

    @Transactional
    @Override
    public ResponseEntity<EmployeeDTO> activateInactivate(Integer id, Boolean active) {
        Employee employee = super.findAndValidate(id);

        employee.setActive(active);

        User user = employee.getUser();

        user.setActive(active);
        user.setEmployee(employee);

        userRepository.save(user);
        employeeRepository.save(employee);

        return ResponseEntity.ok().body(Converter.convertEntityToDTO(employee, EmployeeDTO.class));
    }

    private void resolverUserPhoto(User user, EmployeeBean employeeBean) {
        if (StringUtil.isNotNullOrEmpty(user.getPhoto())) {
            s3StorageService.delete(FileUtil.getFilenameFromS3Url(user.getPhoto()));
        }

        if (Utils.isNotEmpty(employeeBean.getPhoto())) {
            multipartBeanValidator.validate(employeeBean.getPhoto());

            user.setPhoto(s3StorageService.upload(employeeBean.getPhoto(), true));
        } else {
            user.setPhoto(null);
        }
    }

}