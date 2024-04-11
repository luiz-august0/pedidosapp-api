package com.pedidosapp.api.service;

import com.pedidosapp.api.model.beans.EmployeeBean;
import com.pedidosapp.api.model.dtos.EmployeeDTO;
import com.pedidosapp.api.model.entities.Employee;
import com.pedidosapp.api.model.entities.User;
import com.pedidosapp.api.model.enums.EnumUserRole;
import com.pedidosapp.api.repository.EmployeeRepository;
import com.pedidosapp.api.repository.UserRepository;
import com.pedidosapp.api.service.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.service.exceptions.enums.EnumUnauthorizedException;
import com.pedidosapp.api.service.validators.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends AbstractService<EmployeeRepository, Employee, EmployeeDTO, EmployeeValidator> {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    UserRepository userRepository;

    EmployeeService(EmployeeRepository repository) {
        super(repository, new Employee(), new EmployeeDTO(), new EmployeeValidator());
    }

    public ResponseEntity insert(EmployeeBean bean) {
        if (userRepository.findByLogin(bean.getLogin()) != null)
            throw new ApplicationGenericsException(EnumUnauthorizedException.USER_ALREADY_REGISTERED);

        String encryptedPassword = new BCryptPasswordEncoder().encode(bean.getPassword());
        User user = new User(bean.getLogin(), encryptedPassword, EnumUserRole.EMPLOYEE);
        userRepository.save(user);

        Employee employee = new Employee(null, bean.getName(), bean.getCpf(), bean.getContact(), user, true);
        employeeRepository.save(employee);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity update(Integer id, EmployeeBean bean) {
        Employee employee = super.findAndValidate(id);
        User user = (userRepository.findById(employee.getUser().getId())).get();

        if ((bean.getLogin() != null) && (!bean.getLogin().equals(user.getLogin()))) {
            if (userRepository.findByLogin(bean.getLogin()) != null)
                throw new ApplicationGenericsException(EnumUnauthorizedException.USER_ALREADY_REGISTERED);

            user.setLogin(bean.getLogin());
        }

        if (bean.getPassword() != null) {
            user.setPassword(new BCryptPasswordEncoder().encode(bean.getPassword()));
        }

        userRepository.save(user);

        employee.setName(bean.getName());
        employee.setCpf(bean.getCpf());
        employee.setContact(bean.getContact());
        employee.setUser(user);

        employeeRepository.save(employee);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity activateInactivate(Integer id, Boolean active) {
        Employee employee = super.findAndValidate(id);
        User user = employee.getUser();
        employee.setActive(active);
        user.setActive(active);
        user.setEmployee(employee);

        userRepository.save(user);
        employeeRepository.save(employee);
        return ResponseEntity.ok().build();
    }
}