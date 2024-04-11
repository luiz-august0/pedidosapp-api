package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.IOrderItemController;
import com.pedidosapp.api.model.dtos.OrderItemDTO;
import com.pedidosapp.api.service.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderItemController extends AbstractAllGetController<OrderItemService, OrderItemDTO> implements IOrderItemController {

    private final OrderItemService service;

    OrderItemController(OrderItemService service) {
        super(service);
        this.service = service;
    }

    @Override
    public ResponseEntity<OrderItemDTO> insert(OrderItemDTO orderItemDTO) {
        return service.insert(orderItemDTO);
    }

    @Override
    public ResponseEntity<OrderItemDTO> update(Integer id, OrderItemDTO orderItemDTO) {
        return service.update(id, orderItemDTO);
    }

    @Override
    public void delete(Integer id) {
        service.delete(id);
    }
}
