package com.pedidosapp.api.infrastructure.specs;

import com.pedidosapp.api.utils.DateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

import java.util.Date;

public class PredicateBuilder {
    public static Predicate createPredicate(CriteriaBuilder criteriaBuilder, Path path, Object fieldValue, EnumSpecification specification) {
        if (path.getJavaType().equals(Date.class)) {
            fieldValue = DateUtil.parseStringToDate(fieldValue.toString());
        }

        switch (specification) {
            case EQUALS -> {
                return criteriaBuilder.equal(path, fieldValue);
            }
            case LIKE -> {
                return criteriaBuilder.like(criteriaBuilder.upper(path), "%" + fieldValue.toString().toUpperCase() + "%");
            }
            case GREATEROREQUALS -> {
                return criteriaBuilder.greaterThanOrEqualTo(path, (Comparable) fieldValue);
            }
            case LESSOREQUALS -> {
                return criteriaBuilder.lessThanOrEqualTo(path, (Comparable) fieldValue);
            }
            default -> {
                return null;
            }
        }
    }
}
