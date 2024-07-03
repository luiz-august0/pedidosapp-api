package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.IProductController;
import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.model.dtos.ProductDTO;
import com.pedidosapp.api.model.entities.Product;
import com.pedidosapp.api.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController extends AbstractAllController<ProductService, ProductDTO> implements IProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        super(productService);
        this.productService = productService;
    }

    @Override
    public ResponseEntity<ProductDTO> insert(ProductDTO product) {
        return productService.insert(Converter.convertDTOToEntity(product, Product.class));
    }

    @Override
    public ResponseEntity<ProductDTO> update(Integer id, ProductDTO product) {
        return productService.update(id, Converter.convertDTOToEntity(product, Product.class));
    }

}
