package com.pedidosapp.api.service;

import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.model.dtos.UserDTO;
import com.pedidosapp.api.model.entities.Employee;
import com.pedidosapp.api.model.entities.User;
import com.pedidosapp.api.model.enums.EnumUserRole;
import com.pedidosapp.api.repository.UserRepository;
import com.pedidosapp.api.service.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.service.exceptions.enums.EnumUnauthorizedException;
import com.pedidosapp.api.service.validators.UserValidator;
import com.pedidosapp.api.utils.Utils;
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

    public UserService(UserRepository userRepository, EmployeeService employeeService) {
        super(userRepository, new User(), new UserDTO(), new UserValidator(userRepository));
        this.userRepository = userRepository;
        this.validator = new UserValidator(userRepository);
        this.employeeService = employeeService;
    }

    @Override
    @Transactional
    public ResponseEntity<UserDTO> insert(User user) {
        user.setRole(EnumUserRole.ADMIN);

        validator.validate(user);

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
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

        validator.validate(user);

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
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
}