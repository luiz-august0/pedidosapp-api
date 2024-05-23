package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.ISupplierController;
import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.model.dtos.SupplierDTO;
import com.pedidosapp.api.model.entities.Supplier;
import com.pedidosapp.api.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupplierController extends AbstractAllController<SupplierService, SupplierDTO> implements ISupplierController {

    private final SupplierService supplierService;

    SupplierController(SupplierService service) {
        super(service);
        this.supplierService = service;
    }

    @Override
    public ResponseEntity<SupplierDTO> insert(SupplierDTO supplier) {
        return supplierService.insert(Converter.convertDTOToEntity(supplier, Supplier.class));
    }

    @Override
    public ResponseEntity<SupplierDTO> update(Integer id, SupplierDTO supplier) {
        return supplierService.update(id, Converter.convertDTOToEntity(supplier, Supplier.class));
    }
}
