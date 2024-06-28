package com.pedidosapp.api.infrastructure.specs.implementations;

import com.pedidosapp.api.infrastructure.specs.interfaces.IPathFieldValueResolver;
import com.pedidosapp.api.utils.DateUtil;

public class PathFieldValueResolver {

    public static IPathFieldValueResolver getPathFieldValueDateResolver() {
        return fieldValue -> DateUtil.parseStringToDate(fieldValue.toString());
    }

    public static IPathFieldValueResolver getPathFieldValueBooleanResolver() {
        return fieldValue -> Boolean.parseBoolean(fieldValue.toString());
    }

    public static IPathFieldValueResolver getPathFieldValueDefaultResolver() {
        return fieldValue -> fieldValue;
    }

}
