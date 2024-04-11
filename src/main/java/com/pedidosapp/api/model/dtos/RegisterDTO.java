package com.pedidosapp.api.model.dtos;

import com.pedidosapp.api.model.enums.EnumUserRole;

public record RegisterDTO(String login, String password, EnumUserRole role) {
}
