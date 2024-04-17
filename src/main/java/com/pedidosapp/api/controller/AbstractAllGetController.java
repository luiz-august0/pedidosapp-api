package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.IAbstractAllGetController;
import com.pedidosapp.api.model.dtos.AbstractDTO;
import com.pedidosapp.api.service.AbstractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class AbstractAllGetController<Service extends AbstractService, DTO extends AbstractDTO> implements IAbstractAllGetController<DTO>, Serializable {
    private final Service service;

    AbstractAllGetController(Service service) {
        this.service = service;
    }

    public List<DTO> findAll() {
        return service.findAll();
    }

    public List<DTO> findAllFiltered(Map<String, Object> filters) {
        return service.findAllFiltered(filters);
    };

    public Page<DTO> findAllFilteredAndPageable(Map<String, Object> filters, Pageable pageable) {
        return service.findAllFilteredAndPageable(filters, pageable);
    };

    public DTO findById(Integer id) {
        return (DTO) service.findDTOAndValidate(id);
    }
}
