package com.pedidosapp.api.controller.interfaces;

import com.pedidosapp.api.model.dtos.PurchaseOrderItemDTO;
import org.springframework.web.bind.annotation.*;

import static com.pedidosapp.api.constants.Paths.prefixPath;


@RequestMapping(IPurchaseOrderItemController.PATH)
public interface IPurchaseOrderItemController extends IAbstractAllGetController<PurchaseOrderItemDTO> {
    String PATH = prefixPath + "/purchase-order/item";

    @PostMapping
    PurchaseOrderItemDTO insert(@RequestBody PurchaseOrderItemDTO orderItemDTO);

    @PutMapping("/{id}")
    PurchaseOrderItemDTO update(@PathVariable("id") Integer id, @RequestBody PurchaseOrderItemDTO orderItemDTO);

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") Integer id);

}