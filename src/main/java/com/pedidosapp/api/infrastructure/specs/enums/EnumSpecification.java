package com.pedidosapp.api.infrastructure.specs.enums;

import com.pedidosapp.api.infrastructure.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.infrastructure.specs.implementations.PredicateSpec;
import com.pedidosapp.api.infrastructure.specs.interfaces.IPredicateSpec;

import java.util.Arrays;

public enum EnumSpecification {
    EQUALS("EQUALS", "::", PredicateSpec.getPredicateSpecEquals()),
    LIKE("LIKE", ":like:", PredicateSpec.getPredicateSpecLike()),
    GREATEROREQUALS("GREATEROREQUALS", ":>:", PredicateSpec.getPredicateSpecGreaterOrEquals()),
    LESSOREQUALS("LESSOREQUALS", ":<:", PredicateSpec.getPredicateSpecLessOrEquals());

    private String name;

    private String prefix;

    private IPredicateSpec IPredicateSpec;

    EnumSpecification(String name, String prefix, IPredicateSpec IPredicateSpec) {
        this.name = name;
        this.prefix = prefix;
        this.IPredicateSpec = IPredicateSpec;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public IPredicateSpec getPredicateSpecInterface() {
        return IPredicateSpec;
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
