package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.IUserController;
import com.pedidosapp.api.model.records.RegisterRecord;
import com.pedidosapp.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
public class UserController implements IUserController, Serializable {
    @Autowired
    private UserService service;

    @Override
    public ResponseEntity register(RegisterRecord registerRecord) {
        return service.register(registerRecord);
    }
}
