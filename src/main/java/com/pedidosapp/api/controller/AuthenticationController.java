package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.IAuthenticationController;
import com.pedidosapp.api.model.beans.TokenBean;
import com.pedidosapp.api.model.dtos.AuthenticationDTO;
import com.pedidosapp.api.service.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
public class AuthenticationController implements IAuthenticationController, Serializable {
    @Autowired
    private AuthenticationService service;

    @Override
    public ResponseEntity<TokenBean> login(AuthenticationDTO authenticationDTO) {
        return service.login(authenticationDTO);
    }

    @Override
    public ResponseEntity<TokenBean> refreshToken(TokenBean tokenBeanRequest) {
        return service.refreshToken(tokenBeanRequest);
    }
}
