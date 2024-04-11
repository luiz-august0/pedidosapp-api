package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.IPurchaseOrderItemController;
import com.pedidosapp.api.model.dtos.PurchaseOrderItemDTO;
import com.pedidosapp.api.service.PurchaseOrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseOrderItemController extends AbstractAllGetController<PurchaseOrderItemService, PurchaseOrderItemDTO> implements IPurchaseOrderItemController {

    private final PurchaseOrderItemService service;

    PurchaseOrderItemController(PurchaseOrderItemService service) {
        super(service);
        this.service = service;
    }

    @Override
    public ResponseEntity<PurchaseOrderItemDTO> insert(PurchaseOrderItemDTO purchaseOrderItemDTO) {
        return service.insert(purchaseOrderItemDTO);
    }

    @Override
    public ResponseEntity<PurchaseOrderItemDTO> update(Integer id, PurchaseOrderItemDTO purchaseOrderItemDTO) {
        return service.update(id, purchaseOrderItemDTO);
    }

    @Override
    public void delete(Integer id) {
        service.delete(id);
    }
}
