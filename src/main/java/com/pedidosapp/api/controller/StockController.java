package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.IStockController;
import com.pedidosapp.api.model.dtos.StockDTO;
import com.pedidosapp.api.service.StockService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController extends AbstractAllController<StockService, StockDTO> implements IStockController {
    StockController(StockService service) {
        super(service, new StockDTO());
    }
}
