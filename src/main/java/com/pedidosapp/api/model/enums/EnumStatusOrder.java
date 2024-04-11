package com.pedidosapp.api.model.enums;

public enum EnumStatusOrder {
    OPEN("OPEN", "Aberto"),
    CLOSED("CLOSED", "Fechado");

    private String status;
    private String name;

    EnumStatusOrder(String status, String name) {
        this.status = status;
        this.name = name;
    }

    public String getStatus() { return status; }

    public String getName() {
        return name;
    }
}
