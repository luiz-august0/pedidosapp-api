package com.pedidosapp.api.service;

import com.pedidosapp.api.model.dtos.UserDTO;
import com.pedidosapp.api.model.entities.User;
import com.pedidosapp.api.model.records.RegisterRecord;
import com.pedidosapp.api.repository.UserRepository;
import com.pedidosapp.api.service.validators.UserValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class UserService extends AbstractService<UserRepository, User, UserDTO, UserValidator>  {
    private final UserRepository repository;

    private final UserValidator validator;

    public UserService(UserRepository repository) {
        super(repository, new User(), new UserDTO(), new UserValidator(repository));
        this.repository = repository;
        this.validator = new UserValidator(repository);
    }

    public ResponseEntity register(RegisterRecord registerRecord) {
        validator.validate(registerRecord);

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRecord.password());
        User user = new User(registerRecord.login(), encryptedPassword, registerRecord.role());
        repository.save(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}