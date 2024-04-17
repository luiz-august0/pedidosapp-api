package com.pedidosapp.api.infrastructure.specs;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

public class PredicateBuilder {
    public static Predicate createPredicate(CriteriaBuilder criteriaBuilder, Path path, Object fieldValue, EnumSpecification specification) {
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
