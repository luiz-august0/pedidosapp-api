package com.pedidosapp.api.controller.interfaces;

import com.pedidosapp.api.model.dtos.OrderItemDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.pedidosapp.api.constants.paths.Paths.prefixPath;


@RequestMapping(IOrderItemController.PATH)
public interface IOrderItemController extends IAbstractAllGetController<OrderItemDTO> {
    String PATH = prefixPath + "/order/item";

    @PostMapping
    ResponseEntity<OrderItemDTO> insert(@RequestBody OrderItemDTO orderItemDTO);

    @PutMapping("/{id}")
    ResponseEntity<OrderItemDTO> update(@PathVariable("id") Integer id, @RequestBody OrderItemDTO orderItemDTO);

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") Integer id);
}