package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.ISupplierController;
import com.pedidosapp.api.model.dtos.SupplierDTO;
import com.pedidosapp.api.service.SupplierService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupplierController extends AbstractAllController<SupplierService, SupplierDTO> implements ISupplierController {
    SupplierController(SupplierService service) {
        super(service);
    }
}
