package com.pedidosapp.api.controller.interfaces;

import com.pedidosapp.api.model.dtos.RegisterDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.pedidosapp.api.constants.paths.Paths.prefixPath;

@RequestMapping(IUserController.PATH)
public interface IUserController {
    String PATH = prefixPath + "/user";

    @PostMapping("/register")
    ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO);
}