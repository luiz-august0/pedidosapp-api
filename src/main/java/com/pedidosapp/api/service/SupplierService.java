package com.pedidosapp.api.service;

import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.model.dtos.SupplierDTO;
import com.pedidosapp.api.model.entities.Product;
import com.pedidosapp.api.model.entities.Supplier;
import com.pedidosapp.api.repository.SupplierRepository;
import com.pedidosapp.api.service.validators.SupplierValidator;
import com.pedidosapp.api.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierService extends AbstractService<SupplierRepository, Supplier, SupplierDTO, SupplierValidator> {

    private final SupplierRepository supplierRepository;

    private final SupplierValidator supplierValidator;

    SupplierService(SupplierRepository repository, ProductService productService) {
        super(repository, new Supplier(), new SupplierDTO(), new SupplierValidator(repository));
        this.supplierRepository = repository;
        this.supplierValidator = new SupplierValidator(repository);
    }

    @Transactional
    @Override
    public ResponseEntity<SupplierDTO> insert(Supplier supplier) {
        supplierValidator.validate(supplier);

        resolverProducts(supplier);
        Supplier supplierManaged = supplierRepository.save(supplier);

        return ResponseEntity.status(HttpStatus.CREATED).body(Converter.convertEntityToDTO(supplierManaged, SupplierDTO.class));
    }

    @Transactional
    @Override
    public ResponseEntity<SupplierDTO> update(Integer id, Supplier supplier) {
        Supplier supplierManaged = this.findAndValidate(id);
        supplier.setId(supplierManaged.getId());

        supplierValidator.validate(supplier);
        resolverProducts(supplier);
        supplierManaged = supplierRepository.save(supplier);

        return ResponseEntity.ok().body(Converter.convertEntityToDTO(supplierManaged, SupplierDTO.class));
    }

    private void resolverProducts(Supplier supplier) {
        List<Product> productsManaged = new ArrayList<>();
        List<Product> products = supplier.getProducts();

        if (Utils.isNotEmpty(products) && !products.isEmpty()) {
            products.forEach(product -> {
                Integer productId = product.getId();

                productsManaged.removeIf(productManaged -> productManaged.getId().equals(productId));

                product = this.findAndValidateActiveGeneric(Product.class, productId, true);

                product.setSuppliers(null);

                productsManaged.add(product);
            });
        }

        supplier.setProducts(productsManaged);
    }
}