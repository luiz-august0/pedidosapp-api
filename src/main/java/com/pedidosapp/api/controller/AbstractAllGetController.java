package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.IAbstractAllGetController;
import com.pedidosapp.api.model.dtos.AbstractDTO;
import com.pedidosapp.api.service.AbstractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractAllGetController<Service extends AbstractService, DTO extends AbstractDTO> implements IAbstractAllGetController<DTO>, Serializable {
    private final Service service;

    AbstractAllGetController(Service service) {
        this.service = service;
    }

    public List<DTO> findAll() {
        return service.findAll();
    }

    public List<DTO> findAllFiltered(DTO dto) {
        return service.findAllFiltered(dto);
    };

    public Page<DTO> findAllFilteredAndPageable(DTO dto, Pageable pageable) {
        return service.findAllFilteredAndPageable(dto, pageable);
    };

    public DTO findById(Integer id) {
        return (DTO) service.findDTOAndValidate(id);
    }
}
