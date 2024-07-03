package com.pedidosapp.api.service;

import com.pedidosapp.api.external.s3.S3StorageService;
import com.pedidosapp.api.infrastructure.converter.Converter;
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
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends AbstractService<EmployeeRepository, Employee, EmployeeDTO, EmployeeValidator> {

    private final EmployeeRepository employeeRepository;

    private final EmployeeValidator employeeValidator;

    private final MultipartBeanValidator multipartBeanValidator;

    private final S3StorageService s3StorageService;

    EmployeeService(EmployeeRepository employeeRepository, UserRepository userRepository, S3StorageService s3StorageService) {
        super(employeeRepository, new Employee(), new EmployeeDTO(), new EmployeeValidator(employeeRepository, userRepository));
        this.employeeRepository = employeeRepository;
        this.employeeValidator = new EmployeeValidator(employeeRepository, userRepository);
        this.multipartBeanValidator = new MultipartBeanValidator();
        this.s3StorageService = s3StorageService;
    }

    @Transactional
    public ResponseEntity<EmployeeDTO> insert(EmployeeBean bean) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(bean.getPassword());
        User user = new User(bean.getLogin(), encryptedPassword, EnumUserRole.EMPLOYEE);

        user.setActive(bean.getActive());

        resolverUserPhoto(user, bean);

        Employee employee = new Employee(null, bean.getName(), bean.getEmail(), bean.getCpf(), bean.getContact(), user, bean.getActive());

        employeeValidator.validate(employee);

        employeeRepository.save(employee);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Transactional
    public ResponseEntity<EmployeeDTO> update(Integer id, EmployeeBean bean) {
        Employee oldEmployee = super.findAndValidate(id);

        Employee employee = new Employee();

        User user = new User();

        user.setRole(oldEmployee.getUser().getRole());
        user.setId(oldEmployee.getUser().getId());
        user.setPhoto(oldEmployee.getUser().getPhoto());

        user.setLogin(bean.getLogin());
        user.setActive(bean.getActive());

        if (bean.getPassword() != null) {
            user.setPassword(new BCryptPasswordEncoder().encode(bean.getPassword()));
        } else {
            user.setPassword(oldEmployee.getUser().getPassword());
        }

        resolverUserPhoto(user, bean);

        employee.setId(oldEmployee.getId());
        employee.setName(bean.getName());
        employee.setEmail(bean.getEmail());
        employee.setCpf(bean.getCpf());
        employee.setContact(bean.getContact());
        employee.setUser(user);
        employee.setActive(bean.getActive());

        employeeValidator.validate(employee);

        employeeRepository.save(employee);

        return ResponseEntity.ok().body(Converter.convertEntityToDTO(employee, EmployeeDTO.class));
    }

    @Transactional
    @Override
    public ResponseEntity<EmployeeDTO> activateInactivate(Integer id, Boolean active) {
        Employee employee = super.findAndValidate(id);

        employee.setActive(active);
        employee.getUser().setActive(active);

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