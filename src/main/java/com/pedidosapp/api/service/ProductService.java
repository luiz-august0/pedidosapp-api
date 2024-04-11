package com.pedidosapp.api.service;

import com.pedidosapp.api.converter.Converter;
import com.pedidosapp.api.model.dtos.ProductDTO;
import com.pedidosapp.api.model.entities.Product;
import com.pedidosapp.api.model.entities.ProductSupplier;
import com.pedidosapp.api.repository.ProductRepository;
import com.pedidosapp.api.service.validators.ProductValidator;
import com.pedidosapp.api.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService extends AbstractService<ProductRepository, Product, ProductDTO, ProductValidator> {

    private final ProductValidator productValidator;

    private final ProductRepository productRepository;

    private final SupplierService supplierService;

    ProductService(ProductRepository repository, SupplierService supplierService) {
        super(repository, new Product(), new ProductDTO(), new ProductValidator());
        this.productRepository = repository;
        this.supplierService = supplierService;
        this.productValidator = new ProductValidator();
    }

    @Override
    public ResponseEntity<ProductDTO> insert(ProductDTO productDTO) {
        Product product = Converter.convertDTOToEntity(productDTO, Product.class);
        productValidator.validate(product);

        resolverProductSuppliers(product);
        Product productManaged = productRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(Converter.convertEntityToDTO(productManaged, ProductDTO.class));
    }

    @Override
    public ResponseEntity<ProductDTO> update(Integer id, ProductDTO productDTO) {
        Product productManaged = this.findAndValidate(id);
        Product product = Converter.convertDTOToEntity(productDTO, Product.class);
        product.setId(productManaged.getId());

        productValidator.validate(product);
        resolverProductSuppliers(product);
        productManaged = productRepository.save(product);

        return ResponseEntity.ok().body(Converter.convertEntityToDTO(productManaged, ProductDTO.class));
    }

    private void resolverProductSuppliers(Product product) {
        List<ProductSupplier> productSuppliersManaged = new ArrayList<>();
        List<ProductSupplier> productSuppliers = product.getProductSuppliers();

        if (Utils.isNotEmpty(productSuppliers) && !productSuppliers.isEmpty()) {
            productSuppliers.forEach(productSupplier -> {
                productSuppliersManaged.removeIf(productSupplierManaged -> productSupplierManaged.getSupplier().getId().equals(productSupplier.getSupplier().getId()));

                productSupplier.setProduct(product);
                productSupplier.setSupplier(supplierService.findAndValidateActive(productSupplier.getSupplier().getId()));
                productSuppliersManaged.add(productSupplier);
            });
        }

        product.setProductSuppliers(productSuppliersManaged);
    }
}