package com.pedidosapp.api.controller.interfaces;

import com.pedidosapp.api.model.dtos.OrderDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.pedidosapp.api.constants.Paths.prefixPath;


@RequestMapping(IOrderController.PATH)
public interface IOrderController extends IAbstractAllGetController<OrderDTO> {
    String PATH = prefixPath + "/order";

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    OrderDTO insert(@RequestBody OrderDTO orderDTO);

    @PutMapping("/{id}/close")
    OrderDTO closeOrder(@PathVariable("id") Integer id);

}