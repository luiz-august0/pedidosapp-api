package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.IEmployeeController;
import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.model.beans.EmployeeBean;
import com.pedidosapp.api.model.dtos.EmployeeDTO;
import com.pedidosapp.api.service.EmployeeService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController extends AbstractAllGetController<EmployeeService, EmployeeDTO> implements IEmployeeController {

    private final EmployeeService service;

    EmployeeController(EmployeeService service) {
        super(service, new EmployeeDTO());
        this.service = service;
    }

    @Override
    public EmployeeDTO insert(EmployeeBean bean) {
        return Converter.convertEntityToDTO(service.insert(bean), EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO update(Integer id, EmployeeBean bean) {
        return Converter.convertEntityToDTO(service.update(id, bean), EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO activateInactivate(Integer id, Boolean active) {
        return Converter.convertEntityToDTO(service.activateInactivate(id, active), EmployeeDTO.class);
    }

}
