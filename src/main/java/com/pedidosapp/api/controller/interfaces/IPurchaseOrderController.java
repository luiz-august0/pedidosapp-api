package com.pedidosapp.api.controller.interfaces;

import com.pedidosapp.api.model.dtos.PurchaseOrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.pedidosapp.api.constants.paths.Paths.prefixPath;


@RequestMapping(IPurchaseOrderController.PATH)
public interface IPurchaseOrderController extends IAbstractAllGetController<PurchaseOrderDTO> {
    String PATH = prefixPath + "/purchase-order";

    @PostMapping
    ResponseEntity<PurchaseOrderDTO> insert(@RequestBody PurchaseOrderDTO orderDTO);

    @PutMapping("/close/{id}")
    ResponseEntity<PurchaseOrderDTO> closePurchaseOrder(@PathVariable("id") Integer id);

}