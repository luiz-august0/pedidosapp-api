package com.pedidosapp.api.service;

import com.pedidosapp.api.model.entities.Product;
import com.pedidosapp.api.model.entities.Supplier;
import com.pedidosapp.api.repository.ProductRepository;
import com.pedidosapp.api.utils.Utils;
import com.pedidosapp.api.validators.ProductValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService extends AbstractService<ProductRepository, Product, ProductValidator> {

    private final ProductValidator productValidator;

    private final ProductRepository productRepository;

    ProductService(ProductRepository repository) {
        super(repository, new Product(), new ProductValidator());
        this.productRepository = repository;
        this.productValidator = new ProductValidator();
    }

    @Transactional
    @Override
    public Product insert(Product product) {
        productValidator.validate(product);

        resolverSuppliers(product);

        return productRepository.save(product);
    }

    @Transactional
    @Override
    public Product update(Integer id, Product product) {
        Product productManaged = this.findAndValidate(id);

        product.setId(productManaged.getId());

        productValidator.validate(product);

        resolverSuppliers(product);

        productManaged = productRepository.save(product);

        return productManaged;
    }

    private void resolverSuppliers(Product product) {
        List<Supplier> suppliersManaged = new ArrayList<>();
        List<Supplier> suppliers = product.getSuppliers();

        if (Utils.isNotEmpty(suppliers) && !suppliers.isEmpty()) {
            suppliers.forEach(supplier -> {
                Integer supplierId = supplier.getId();

                suppliersManaged.removeIf(supplierManaged -> supplierManaged.getId().equals(supplierId));

                supplier = this.findAndValidateActiveGeneric(Supplier.class, supplierId, true);

                supplier.getProducts().remove(product);

                suppliersManaged.add(supplier);
            });
        }

        product.setSuppliers(suppliersManaged);
    }

}