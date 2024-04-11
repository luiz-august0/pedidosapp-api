package com.pedidosapp.api.model.enums;

public enum EnumUnitProduct {
    UNIT("UNIT", "Unidade"),
    KILOGRAM("KILOGRAM", "Kilograma"),
    PACKAGE("PACKAGE", "Pacote");

    private String unit;
    private String name;

    EnumUnitProduct(String unit, String name) {
        this.unit = unit;
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public String getName() {
        return name;
    }
}
