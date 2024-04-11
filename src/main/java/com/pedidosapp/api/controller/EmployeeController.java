package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.IEmployeeController;
import com.pedidosapp.api.model.beans.EmployeeBean;
import com.pedidosapp.api.model.dtos.EmployeeDTO;
import com.pedidosapp.api.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController extends AbstractAllGetController<EmployeeService, EmployeeDTO> implements IEmployeeController {
    @Autowired
    EmployeeService service;

    EmployeeController(EmployeeService service) {
        super(service);
    }

    public ResponseEntity insert(EmployeeBean bean) {
        return service.insert(bean);
    }

    public ResponseEntity update(Integer id, EmployeeBean bean) {
        return service.update(id, bean);
    }

    public ResponseEntity activateInactivate(Integer id, Boolean active) {
        return service.activateInactivate(id, active);
    }
}
