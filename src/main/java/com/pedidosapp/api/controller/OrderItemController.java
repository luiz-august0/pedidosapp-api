package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.IOrderItemController;
import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.model.dtos.OrderItemDTO;
import com.pedidosapp.api.model.entities.OrderItem;
import com.pedidosapp.api.service.OrderItemService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderItemController extends AbstractAllGetController<OrderItemService, OrderItemDTO> implements IOrderItemController {

    private final OrderItemService service;

    OrderItemController(OrderItemService service) {
        super(service, new OrderItemDTO());
        this.service = service;
    }

    @Override
    public OrderItemDTO insert(OrderItemDTO orderItemDTO) {
        return Converter.convertEntityToDTO(service.insert(Converter.convertDTOToEntity(orderItemDTO, OrderItem.class)), OrderItemDTO.class);
    }

    @Override
    public OrderItemDTO update(Integer id, OrderItemDTO orderItemDTO) {
        return Converter.convertEntityToDTO(service.update(id, Converter.convertDTOToEntity(orderItemDTO, OrderItem.class)), OrderItemDTO.class);
    }

    @Override
    public void delete(Integer id) {
        service.delete(id);
    }

}
