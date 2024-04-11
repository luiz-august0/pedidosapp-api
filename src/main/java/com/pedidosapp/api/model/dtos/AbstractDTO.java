package com.pedidosapp.api.model.dtos;

import com.pedidosapp.api.model.entities.AbstractEntity;
import com.pedidosapp.api.service.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.utils.Utils;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDTO<Entity extends AbstractEntity> implements Serializable {
    public Specification<Entity> toSpec(Entity entity) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Field[] fields = entity.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);

                try {
                    createSpecification(predicates, field.get(entity), field.getName(), root, criteriaBuilder, entity);
                } catch (Exception e) {
                    throw new ApplicationGenericsException(e.getMessage());
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private void createSpecification(List<Predicate> predicates,
                                     Object fieldValue,
                                     String fieldName,
                                     Root root,
                                     CriteriaBuilder criteriaBuilder,
                                     Entity entity) throws IllegalAccessException {
        if (Utils.isNotEmpty(fieldValue)) {
            Path fieldSpec = root.get(fieldName);
            Predicate predicate = null;
            
            if (fieldValue.getClass().equals(String.class)) {
                predicate = criteriaBuilder.like(criteriaBuilder.upper(fieldSpec), "%"+fieldValue.toString().toUpperCase()+"%");
            } else if (fieldValue instanceof AbstractEntity) {
                Join<Entity, Class<? extends AbstractEntity>> join = root.join(fieldName, JoinType.INNER);
                Field[] classFields = fieldValue.getClass().getDeclaredFields();

                for (Field classField : classFields) {
                    classField.setAccessible(true);

                    if (Utils.isNotEmpty(classField.get(fieldValue)) && !classField.getType().equals(entity.getClass())) {
                        if (classField.getType().equals(String.class)) {
                            Path classFieldSpec = join.get(classField.getName());
                            predicate = criteriaBuilder.like(criteriaBuilder.upper(classFieldSpec), "%"+classField.get(fieldValue).toString().toUpperCase()+"%");
                        } else {
                            predicate = criteriaBuilder.equal(join.get(classField.getName()), classField.get(fieldValue));
                        }

                        predicates.add(predicate);
                    }
                }

            } else {
                predicate = criteriaBuilder.equal(fieldSpec, fieldValue);
            }

            predicates.add(predicate);
        }
    }
}
