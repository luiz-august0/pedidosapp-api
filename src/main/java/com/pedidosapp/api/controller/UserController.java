package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.IUserController;
import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.model.dtos.UserDTO;
import com.pedidosapp.api.model.entities.User;
import com.pedidosapp.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends AbstractAllController<UserService, UserDTO> implements IUserController {

    private final UserService userService;

    UserController(UserService userService) {
        super(userService);
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserDTO> insert(UserDTO user) {
        return userService.insert(Converter.convertDTOToEntity(user, User.class));
    }

    @Override
    public ResponseEntity<UserDTO> update(Integer id, UserDTO user) {
        return userService.update(id, Converter.convertDTOToEntity(user, User.class));
    }

    @Override
    public ResponseEntity<UserDTO> activateInactivate(Integer id, Boolean active) {
        return userService.activateInactivate(id, active);
    }
}
