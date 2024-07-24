package com.pedidosapp.api.controller.interfaces;

import com.pedidosapp.api.model.dtos.OrderItemDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.pedidosapp.api.constants.Paths.prefixPath;


@RequestMapping(IOrderItemController.PATH)
public interface IOrderItemController extends IAbstractAllGetController<OrderItemDTO> {
    String PATH = prefixPath + "/order/item";

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    OrderItemDTO insert(@RequestBody OrderItemDTO orderItemDTO);

    @PutMapping("/{id}")
    OrderItemDTO update(@PathVariable("id") Integer id, @RequestBody OrderItemDTO orderItemDTO);

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") Integer id);

}