package com.pedidosapp.api.service;

import com.pedidosapp.api.model.entities.Stock;
import com.pedidosapp.api.repository.StockRepository;
import com.pedidosapp.api.validators.StockValidator;
import org.springframework.stereotype.Service;

@Service
public class StockService extends AbstractService<StockRepository, Stock, StockValidator> {
    StockService(StockRepository repository) {
        super(repository, new Stock(), new StockValidator());
    }

}