package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.IProductController;
import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.model.dtos.ProductDTO;
import com.pedidosapp.api.model.entities.Product;
import com.pedidosapp.api.service.ProductService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController extends AbstractAllController<ProductService, ProductDTO> implements IProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        super(productService, new ProductDTO());
        this.productService = productService;
    }

    @Override
    public ProductDTO insert(ProductDTO product) {
        return Converter.convertEntityToDTO(productService.insert(Converter.convertDTOToEntity(product, Product.class)), ProductDTO.class);
    }

    @Override
    public ProductDTO update(Integer id, ProductDTO product) {
        return Converter.convertEntityToDTO(productService.update(id, Converter.convertDTOToEntity(product, Product.class)), ProductDTO.class);
    }

}
