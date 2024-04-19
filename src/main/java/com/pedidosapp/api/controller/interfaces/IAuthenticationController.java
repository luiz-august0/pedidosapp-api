package com.pedidosapp.api.controller.interfaces;

import com.pedidosapp.api.model.beans.TokenBean;
import com.pedidosapp.api.model.records.AuthenticationRecord;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.pedidosapp.api.constants.Paths.prefixPath;


@RequestMapping(IAuthenticationController.PATH)
public interface IAuthenticationController {
    String PATH = prefixPath + "/session";

    @PostMapping("/login")
    ResponseEntity<TokenBean> login(@RequestBody @Valid AuthenticationRecord authenticationRecord);

    @PostMapping("/refresh-token")
    ResponseEntity<TokenBean> refreshToken(@RequestBody TokenBean tokenBeanRequest);
}