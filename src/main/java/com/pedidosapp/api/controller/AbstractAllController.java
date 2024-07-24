package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.IAbstractAllController;
import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.model.dtos.AbstractDTO;
import com.pedidosapp.api.service.AbstractService;

import java.io.Serializable;

public abstract class AbstractAllController<Service extends AbstractService, DTO extends AbstractDTO>
        extends AbstractAllGetController implements IAbstractAllController<DTO>, Serializable {
    private final Service service;

    private final DTO dto;

    AbstractAllController(Service service, DTO dto) {
        super(service, dto);
        this.service = service;
        this.dto = dto;
    }

    public DTO insert(DTO dto) {
        return (DTO) Converter.convertEntityToDTO(service.insert(Converter.convertDTOToEntity(dto, service.entity.getClass())), dto.getClass());
    }

    public DTO activateInactivate(Integer id, Boolean active) {
        return (DTO) Converter.convertEntityToDTO(service.activateInactivate(id, active), dto.getClass());
    }

    public DTO update(Integer id, DTO dto) {
        return (DTO) Converter.convertEntityToDTO(service.update(id, Converter.convertDTOToEntity(dto, service.entity.getClass())), dto.getClass());
    }

}
