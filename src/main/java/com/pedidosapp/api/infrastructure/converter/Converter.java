package com.pedidosapp.api.infrastructure.converter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pedidosapp.api.model.dtos.AbstractDTO;
import com.pedidosapp.api.model.entities.AbstractEntity;
import com.pedidosapp.api.service.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.utils.ClassUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.lang.reflect.Field;
import java.util.*;

public class Converter {
    private static final ModelMapper mapper = new ModelMapper();

    public static <Entity extends AbstractEntity, DTO extends AbstractDTO> DTO convertEntityToDTO(Entity entity, Class<DTO> dtoClass) {
        DTO dtoMapped = mapperEntityToDTO(entity, dtoClass);

        return dtoMapped;
    }

    public static <Entity extends AbstractEntity, DTO extends AbstractDTO> Entity convertDTOToEntity(DTO dto, Class<Entity> entityClass) {
        return mapper.map(dto, entityClass);
    }

    public static <Entity extends AbstractEntity, DTO extends AbstractDTO> List<?> convertListEntityToDTO(List<Entity> entityList, Class<DTO> dtoClass) {
        return entityList.stream().map(entity -> convertEntityToDTO(entity, dtoClass)).toList();
    }

    public static <Entity extends AbstractEntity, DTO extends AbstractDTO> List<?> convertListDTOToEntity(List<DTO> dtoList, Class<Entity> entityClass) {
        return dtoList.stream().map(dto -> convertDTOToEntity(dto, entityClass)).toList();
    }

    public static <Entity extends AbstractEntity, DTO extends AbstractDTO> Page<DTO> convertPageEntityToDTO(Page<Entity> entityPage, Class<DTO> dtoClass) {
        return new PageImpl<>(
                entityPage.stream().map(entity -> convertEntityToDTO(entity, dtoClass)).toList(),
                entityPage.getPageable(),
                entityPage.getTotalElements()
        );
    }

    public static <Entity extends AbstractEntity, DTO extends AbstractDTO> Page<Entity> convertPageDTOToEntity(Page<DTO> dtoPage, Class<Entity> entityClass) {
        return new PageImpl<>(
                dtoPage.stream().map(dto -> convertDTOToEntity(dto, entityClass)).toList(),
                dtoPage.getPageable(),
                dtoPage.getTotalElements()
        );
    }

    private static <Entity extends AbstractEntity, DTO extends AbstractDTO> DTO mapperEntityToDTO(Entity entity, Class<DTO> dtoClass) {
        try {
            DTO dtoObj = dtoClass.getDeclaredConstructor().newInstance();
            List<Field> dtoFields = Arrays.stream(dtoClass.getDeclaredFields()).toList().stream().filter(dtoField ->
                    (!dtoField.isAnnotationPresent(JsonProperty.class)) ||
                            (dtoField.isAnnotationPresent(JsonProperty.class) && !dtoField.getAnnotation(JsonProperty.class).access().equals(JsonProperty.Access.WRITE_ONLY))
            ).toList();

            dtoFields.forEach(dtoField -> {
                try {
                    Field entityField = entity.getClass().getDeclaredField(dtoField.getName());
                    entityField.setAccessible(true);
                    dtoField.setAccessible(true);
                    Object entityFieldValue = entityField.get(entity);

                    if (entityFieldValue instanceof AbstractEntity) {
                        DTO dtoFieldValue = mapperEntityToDTO((Entity) entityFieldValue, (Class<DTO>) dtoField.getType());
                        dtoField.set(dtoObj, dtoFieldValue);
                    } else if (entityFieldValue instanceof Collection<?> entityCollection) {
                        if (!entityCollection.isEmpty()) {
                            Class<?> entityCollectionClass = ClassUtil.getClassFromCollectionField(entityField);

                            if (entityCollectionClass.getSuperclass().equals(AbstractEntity.class)) {
                                Class<DTO> dtoCollectionClass = (Class<DTO>) ClassUtil.getClassFromCollectionField(dtoField);
                                Collection<DTO> dtoCollection;

                                if (entityCollection instanceof HashSet<?>) {
                                    dtoCollection = new HashSet<>();
                                } else {
                                    dtoCollection = new ArrayList<>();
                                }

                                entityCollection.forEach(entityCollectionFieldValue -> {
                                    DTO dtoFieldValue = mapperEntityToDTO((Entity) entityCollectionFieldValue, dtoCollectionClass);
                                    dtoCollection.add(dtoFieldValue);
                                });

                                dtoField.set(dtoObj, dtoCollection);
                            } else {
                                dtoField.set(dtoObj, entityFieldValue);
                            }
                        }
                    } else {
                        dtoField.set(dtoObj, entityFieldValue);
                    }

                } catch (Exception e) {
                    throw new ApplicationGenericsException(e.getMessage());
                }
            });

            return dtoObj;
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }
}