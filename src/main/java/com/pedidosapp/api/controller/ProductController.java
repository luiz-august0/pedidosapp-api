package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.IProductController;
import com.pedidosapp.api.model.dtos.ProductDTO;
import com.pedidosapp.api.service.ProductService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController extends AbstractAllController<ProductService, ProductDTO> implements IProductController {
    ProductController(ProductService service) {
        super(service);
    }
}
