package com.pedidosapp.api.service;

import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.model.dtos.ProductDTO;
import com.pedidosapp.api.model.entities.Product;
import com.pedidosapp.api.model.entities.Supplier;
import com.pedidosapp.api.repository.ProductRepository;
import com.pedidosapp.api.utils.Utils;
import com.pedidosapp.api.validators.ProductValidator;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService extends AbstractService<ProductRepository, Product, ProductDTO, ProductValidator> {

    private final ProductValidator productValidator;

    private final ProductRepository productRepository;

    ProductService(ProductRepository repository) {
        super(repository, new Product(), new ProductDTO(), new ProductValidator());
        this.productRepository = repository;
        this.productValidator = new ProductValidator();
    }

    @Transactional
    @Override
    public ResponseEntity<ProductDTO> insert(Product product) {
        productValidator.validate(product);

        resolverSuppliers(product);
        Product productManaged = productRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(Converter.convertEntityToDTO(productManaged, ProductDTO.class));
    }

    @Transactional
    @Override
    public ResponseEntity<ProductDTO> update(Integer id, Product product) {
        Product productManaged = this.findAndValidate(id);
        product.setId(productManaged.getId());

        productValidator.validate(product);
        resolverSuppliers(product);
        productManaged = productRepository.save(product);

        return ResponseEntity.ok().body(Converter.convertEntityToDTO(productManaged, ProductDTO.class));
    }

    private void resolverSuppliers(Product product) {
        List<Supplier> suppliersManaged = new ArrayList<>();
        List<Supplier> suppliers = product.getSuppliers();

        if (Utils.isNotEmpty(suppliers) && !suppliers.isEmpty()) {
            suppliers.forEach(supplier -> {
                Integer supplierId = supplier.getId();

                suppliersManaged.removeIf(supplierManaged -> supplierManaged.getId().equals(supplierId));

                supplier = this.findAndValidateActiveGeneric(Supplier.class, supplierId, true);

                supplier.setProducts(null);

                suppliersManaged.add(supplier);
            });
        }

        product.setSuppliers(suppliersManaged);
    }
}