package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.IPurchaseOrderController;
import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.model.dtos.PurchaseOrderDTO;
import com.pedidosapp.api.model.entities.PurchaseOrder;
import com.pedidosapp.api.service.PurchaseOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseOrderController extends AbstractAllGetController<PurchaseOrderService, PurchaseOrderDTO> implements IPurchaseOrderController {
    private final PurchaseOrderService service;

    PurchaseOrderController(PurchaseOrderService service) {
        super(service);
        this.service = service;
    }

    @Override
    public ResponseEntity<PurchaseOrderDTO> insert(PurchaseOrderDTO purchaseOrderDTO) {
        return service.insert(Converter.convertDTOToEntity(purchaseOrderDTO, PurchaseOrder.class));
    }

    @Override
    public ResponseEntity<PurchaseOrderDTO> closePurchaseOrder(Integer id) {
        return service.closePurchaseOrder(id);
    }
}
