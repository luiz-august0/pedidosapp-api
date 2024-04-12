package com.pedidosapp.api.controller.interfaces;

import com.pedidosapp.api.model.dtos.SupplierDTO;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.pedidosapp.api.constants.Paths.prefixPath;


@RequestMapping(ISupplierController.PATH)
public interface ISupplierController extends IAbstractAllController<SupplierDTO> {
    String PATH = prefixPath + "/supplier";

}