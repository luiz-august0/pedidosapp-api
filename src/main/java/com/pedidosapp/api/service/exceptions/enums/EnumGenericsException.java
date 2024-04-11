package com.pedidosapp.api.service.exceptions.enums;

public enum EnumGenericsException {
    GENERATE_TOKEN("Ocorreu um erro ao gerar o token de acesso"),
    GENERATE_REFRESH_TOKEN("Ocorreu um erro ao gerar o token de atualização"),
    VALIDATE_TOKEN("Ocorreu um erro ao validar o token"),
    EXPIRED_TOKEN("Token expirado, realize o login novamente"),
    TOKEN_WITHOUT_SCHEMA("Não foi possível localizar schema no token");
    private final String message;

    EnumGenericsException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
