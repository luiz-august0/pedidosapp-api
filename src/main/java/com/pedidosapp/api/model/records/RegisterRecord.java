package com.pedidosapp.api.model.records;

import com.pedidosapp.api.model.enums.EnumUserRole;

public record RegisterRecord(String login, String password, EnumUserRole role) {
}
