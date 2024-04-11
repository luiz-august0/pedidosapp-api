package com.pedidosapp.api.controller.interfaces;

import com.pedidosapp.api.model.dtos.ProductDTO;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.pedidosapp.api.constants.paths.Paths.prefixPath;


@RequestMapping(IProductController.PATH)
public interface IProductController extends IAbstractAllController<ProductDTO> {
    String PATH = prefixPath + "/product";

}