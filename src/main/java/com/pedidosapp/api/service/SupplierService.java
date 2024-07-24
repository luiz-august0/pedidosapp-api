package com.pedidosapp.api.service;

import com.pedidosapp.api.model.entities.Product;
import com.pedidosapp.api.model.entities.Supplier;
import com.pedidosapp.api.repository.SupplierRepository;
import com.pedidosapp.api.utils.Utils;
import com.pedidosapp.api.validators.SupplierValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierService extends AbstractService<SupplierRepository, Supplier, SupplierValidator> {

    private final SupplierRepository supplierRepository;

    private final SupplierValidator supplierValidator;

    SupplierService(SupplierRepository repository) {
        super(repository, new Supplier(), new SupplierValidator(repository));
        this.supplierRepository = repository;
        this.supplierValidator = new SupplierValidator(repository);
    }

    @Transactional
    @Override
    public Supplier insert(Supplier supplier) {
        supplierValidator.validate(supplier);

        resolverProducts(supplier);

        return supplierRepository.save(supplier);
    }

    @Transactional
    @Override
    public Supplier update(Integer id, Supplier supplier) {
        Supplier supplierManaged = this.findAndValidate(id);

        supplier.setId(supplierManaged.getId());

        supplierValidator.validate(supplier);

        resolverProducts(supplier);

        supplierManaged = supplierRepository.save(supplier);

        return supplierManaged;
    }

    private void resolverProducts(Supplier supplier) {
        List<Product> productsManaged = new ArrayList<>();
        List<Product> products = supplier.getProducts();

        if (Utils.isNotEmpty(products) && !products.isEmpty()) {
            products.forEach(product -> {
                Integer productId = product.getId();

                productsManaged.removeIf(productManaged -> productManaged.getId().equals(productId));

                product = this.findAndValidateActiveGeneric(Product.class, productId, true);

                product.getSuppliers().remove(supplier);

                productsManaged.add(product);
            });
        }

        supplier.setProducts(productsManaged);
    }

}