package com.pedidosapp.api.controller.interfaces;

import com.pedidosapp.api.model.dtos.StockDTO;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.pedidosapp.api.constants.paths.Paths.prefixPath;


@RequestMapping(IStockController.PATH)
public interface IStockController extends IAbstractAllController<StockDTO> {
    String PATH = prefixPath + "/stock";

}