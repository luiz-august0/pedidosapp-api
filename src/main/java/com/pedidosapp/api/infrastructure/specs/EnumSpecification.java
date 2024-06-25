package com.pedidosapp.api.infrastructure.specs;

import com.pedidosapp.api.infrastructure.exceptions.ApplicationGenericsException;

import java.util.Arrays;

public enum EnumSpecification {
    EQUALS("EQUALS", "::"),
    LIKE("LIKE", ":like:"),
    GREATEROREQUALS("GREATEROREQUALS", ":>:"),
    LESSOREQUALS("LESSOREQUALS", ":<:");

    private String name;

    private String prefix;

    EnumSpecification(String name, String prefix) {
        this.name = name;
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public static EnumSpecification getEnumByPrefix(String prefixParam) {
        try {
            return Arrays.stream(EnumSpecification.values()).filter(
                    enumSpecification -> enumSpecification.getPrefix().equals(prefixParam)
            ).toList().getFirst();
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }
}
