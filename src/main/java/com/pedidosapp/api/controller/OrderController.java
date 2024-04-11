package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.IOrderController;
import com.pedidosapp.api.model.dtos.OrderDTO;
import com.pedidosapp.api.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController extends AbstractAllGetController<OrderService, OrderDTO> implements IOrderController {
    private final OrderService service;

    OrderController(OrderService service) {
        super(service);
        this.service = service;
    }

    @Override
    public ResponseEntity<OrderDTO> insert(OrderDTO orderDTO) {
        return service.insert(orderDTO);
    }

    @Override
    public ResponseEntity<OrderDTO> closeOrder(Integer id) {
        return service.closeOrder(id);
    }
}
