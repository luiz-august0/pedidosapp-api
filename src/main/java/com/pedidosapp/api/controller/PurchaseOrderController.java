package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.IPurchaseOrderController;
import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.model.dtos.PurchaseOrderDTO;
import com.pedidosapp.api.model.entities.PurchaseOrder;
import com.pedidosapp.api.service.PurchaseOrderService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseOrderController extends AbstractAllGetController<PurchaseOrderService, PurchaseOrderDTO> implements IPurchaseOrderController {
    private final PurchaseOrderService service;

    PurchaseOrderController(PurchaseOrderService service) {
        super(service, new PurchaseOrderDTO());
        this.service = service;
    }

    @Override
    public PurchaseOrderDTO insert(PurchaseOrderDTO purchaseOrderDTO) {
        return Converter.convertEntityToDTO(service.insert(Converter.convertDTOToEntity(purchaseOrderDTO, PurchaseOrder.class)), PurchaseOrderDTO.class);
    }

    @Override
    public PurchaseOrderDTO closePurchaseOrder(Integer id) {
        return Converter.convertEntityToDTO(service.closePurchaseOrder(id), PurchaseOrderDTO.class);
    }

}
