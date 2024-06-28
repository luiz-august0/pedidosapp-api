package com.pedidosapp.api.infrastructure.specs.builders;

import com.pedidosapp.api.infrastructure.specs.enums.EnumSpecification;
import com.pedidosapp.api.infrastructure.specs.strategy.PathStrategy;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

public class PredicateBuilder {
    public static Predicate createPredicate(CriteriaBuilder criteriaBuilder, Path path, Object fieldValue, EnumSpecification specification) {
        Object parsedFieldValue = PathStrategy.getPathStrategy(path).parseFieldValuePath(fieldValue);

        return specification.getPredicateSpecInterface().buildPredicate(criteriaBuilder, path, parsedFieldValue);
    }

}
