package com.pedidosapp.api.controller.interfaces;

import com.pedidosapp.api.model.dtos.CustomerDTO;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.pedidosapp.api.constants.paths.Paths.prefixPath;

@RequestMapping(ICustomerController.PATH)
public interface ICustomerController extends IAbstractAllController<CustomerDTO> {
    String PATH = prefixPath + "/customer";

}