package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.ISupplierController;
import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.model.dtos.SupplierDTO;
import com.pedidosapp.api.model.entities.Supplier;
import com.pedidosapp.api.service.SupplierService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupplierController extends AbstractAllController<SupplierService, SupplierDTO> implements ISupplierController {

    private final SupplierService supplierService;

    SupplierController(SupplierService service) {
        super(service, new SupplierDTO());
        this.supplierService = service;
    }

    @Override
    public SupplierDTO insert(SupplierDTO supplier) {
        return Converter.convertEntityToDTO(supplierService.insert(Converter.convertDTOToEntity(supplier, Supplier.class)), SupplierDTO.class);
    }

    @Override
    public SupplierDTO update(Integer id, SupplierDTO supplier) {
        return Converter.convertEntityToDTO(supplierService.update(id, Converter.convertDTOToEntity(supplier, Supplier.class)), SupplierDTO.class);
    }

}
