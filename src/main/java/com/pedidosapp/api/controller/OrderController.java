package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.IOrderController;
import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.model.dtos.OrderDTO;
import com.pedidosapp.api.model.entities.Order;
import com.pedidosapp.api.service.OrderService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController extends AbstractAllGetController<OrderService, OrderDTO> implements IOrderController {
    private final OrderService service;

    OrderController(OrderService service) {
        super(service, new OrderDTO());
        this.service = service;
    }

    @Override
    public OrderDTO insert(OrderDTO orderDTO) {
        return Converter.convertEntityToDTO(service.insert(Converter.convertDTOToEntity(orderDTO, Order.class)), OrderDTO.class);
    }

    @Override
    public OrderDTO closeOrder(Integer id) {
        return Converter.convertEntityToDTO(service.closeOrder(id), OrderDTO.class);
    }

}
