package com.pedidosapp.api.controller.interfaces;

import com.pedidosapp.api.model.beans.TokenBean;
import com.pedidosapp.api.model.dtos.UserDTO;
import com.pedidosapp.api.model.records.AuthenticationRecord;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import static com.pedidosapp.api.constants.Paths.prefixPath;


@RequestMapping(IAuthenticationController.PATH)
public interface IAuthenticationController {
    String PATH = prefixPath + "/session";

    @PostMapping("/login")
    TokenBean login(@RequestBody @Valid AuthenticationRecord authenticationRecord);

    @PostMapping("/refresh-token")
    TokenBean refreshToken(@RequestBody TokenBean tokenBeanRequest);

    @PutMapping("/user")
    UserDTO updateSessionUser(@RequestBody UserDTO user);

    @GetMapping
    TokenBean getSession();

}