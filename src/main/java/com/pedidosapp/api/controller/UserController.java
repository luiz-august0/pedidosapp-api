package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.IUserController;
import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.model.dtos.UserDTO;
import com.pedidosapp.api.model.entities.User;
import com.pedidosapp.api.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserController extends AbstractAllController<UserService, UserDTO> implements IUserController {

    private final UserService userService;

    UserController(UserService userService) {
        super(userService, new UserDTO());
        this.userService = userService;
    }

    @Override
    public UserDTO insert(UserDTO user) {
        return Converter.convertEntityToDTO(userService.insert(Converter.convertDTOToEntity(user, User.class)), UserDTO.class);
    }

    @Override
    public UserDTO update(Integer id, UserDTO user) {
        return Converter.convertEntityToDTO(userService.update(id, Converter.convertDTOToEntity(user, User.class)), UserDTO.class);
    }

    @Override
    public UserDTO activateInactivate(Integer id, Boolean active) {
        return Converter.convertEntityToDTO(userService.activateInactivate(id, active), UserDTO.class);
    }

    @Override
    public List<UserDTO> findAllFiltered(Pageable pageable, Map filters) {
        filters.put("id:<>:", userService.getUserByContext().getId());

        return super.findAllFiltered(pageable, filters);
    }

    @Override
    public Page<UserDTO> findAllFilteredAndPageable(Pageable pageable, Map filters) {
        filters.put("id:<>:", userService.getUserByContext().getId());

        return super.findAllFilteredAndPageable(pageable, filters);
    }

}
