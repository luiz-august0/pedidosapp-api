package com.pedidosapp.api.controller.interfaces;

import com.pedidosapp.api.model.dtos.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.pedidosapp.api.constants.Paths.prefixPath;

@RequestMapping(IUserController.PATH)
public interface IUserController extends IAbstractAllController<UserDTO> {

    String PATH = prefixPath + "/user";

    @PutMapping
    ResponseEntity<UserDTO> updateContextUser(@RequestBody UserDTO user);

}