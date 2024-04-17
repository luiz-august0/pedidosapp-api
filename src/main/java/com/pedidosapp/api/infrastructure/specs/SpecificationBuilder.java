package com.pedidosapp.api.infrastructure.specs;

import com.pedidosapp.api.model.entities.AbstractEntity;
import com.pedidosapp.api.service.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.utils.Utils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpecificationBuilder {
    public static <Entity extends AbstractEntity> Specification<Entity> toSpec(Map<String, Object> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            filters.forEach((filterKey, filterValue) -> {
                String prefix = filterKey.substring(filterKey.indexOf(":"), filterKey.lastIndexOf(":") + 1);
                String fieldName = filterKey.substring(0, filterKey.indexOf(":"));
                EnumSpecification specification = EnumSpecification.getEnumByPrefix(prefix);

                try {
                    createSpecification(predicates, filterValue, fieldName, root, criteriaBuilder, specification);
                } catch (Exception e) {
                    throw new ApplicationGenericsException(e.getMessage());
                }
            });

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static <Entity extends AbstractEntity> void createSpecification(List<Predicate> predicates,
                                                                            Object fieldValue,
                                                                            String fieldName,
                                                                            Root<Entity> root,
                                                                            CriteriaBuilder criteriaBuilder,
                                                                            EnumSpecification specification) {
        try {
            if (Utils.isNotEmpty(fieldValue)) {
                Path path = root.get(fieldName);

                Predicate predicate = PredicateBuilder.createPredicate(criteriaBuilder, path, fieldValue, specification);

                if (Utils.isNotEmpty(predicate)) {
                    predicates.add(predicate);
                }
            }
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }
}
