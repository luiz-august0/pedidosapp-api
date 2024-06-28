package com.pedidosapp.api.infrastructure.specs.strategy;

import com.pedidosapp.api.infrastructure.specs.implementations.PathFieldValueResolver;
import com.pedidosapp.api.infrastructure.specs.interfaces.IPathFieldValueResolver;
import jakarta.persistence.criteria.Path;

import java.util.Date;

public class PathStrategy {

    public static IPathFieldValueResolver getPathStrategy(Path path) {
        if (path.getJavaType().equals(Date.class)) {
            return PathFieldValueResolver.getPathFieldValueDateResolver();
        }

        if (path.getJavaType().equals(Boolean.class)) {
            return PathFieldValueResolver.getPathFieldValueBooleanResolver();
        }

        return PathFieldValueResolver.getPathFieldValueDefaultResolver();
    }

}
