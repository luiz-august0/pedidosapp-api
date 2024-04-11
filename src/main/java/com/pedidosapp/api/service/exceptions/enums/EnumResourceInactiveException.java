package com.pedidosapp.api.service.exceptions.enums;

public enum EnumResourceInactiveException {
    RESOURCE_INACTIVE("não está ativo");

    private final String message;

    EnumResourceInactiveException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
