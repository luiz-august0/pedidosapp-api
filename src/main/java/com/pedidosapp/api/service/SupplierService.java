package com.pedidosapp.api.service;

import com.pedidosapp.api.model.dtos.SupplierDTO;
import com.pedidosapp.api.model.entities.Supplier;
import com.pedidosapp.api.repository.SupplierRepository;
import com.pedidosapp.api.service.validators.SupplierValidator;
import org.springframework.stereotype.Service;

@Service
public class SupplierService extends AbstractService<SupplierRepository, Supplier, SupplierDTO, SupplierValidator> {
    SupplierService(SupplierRepository repository) {
        super(repository, new Supplier(), new SupplierDTO(), new SupplierValidator());
    }
}