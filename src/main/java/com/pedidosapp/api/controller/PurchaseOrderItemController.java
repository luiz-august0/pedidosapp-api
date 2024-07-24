package com.pedidosapp.api.controller;

import com.pedidosapp.api.controller.interfaces.IPurchaseOrderItemController;
import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.model.dtos.PurchaseOrderItemDTO;
import com.pedidosapp.api.model.entities.PurchaseOrderItem;
import com.pedidosapp.api.service.PurchaseOrderItemService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseOrderItemController extends AbstractAllGetController<PurchaseOrderItemService, PurchaseOrderItemDTO> implements IPurchaseOrderItemController {

    private final PurchaseOrderItemService service;

    PurchaseOrderItemController(PurchaseOrderItemService service) {
        super(service, new PurchaseOrderItemDTO());
        this.service = service;
    }

    @Override
    public PurchaseOrderItemDTO insert(PurchaseOrderItemDTO purchaseOrderItemDTO) {
        return Converter.convertEntityToDTO(service.insert(Converter.convertDTOToEntity(purchaseOrderItemDTO, PurchaseOrderItem.class)), PurchaseOrderItemDTO.class);
    }

    @Override
    public PurchaseOrderItemDTO update(Integer id, PurchaseOrderItemDTO purchaseOrderItemDTO) {
        return Converter.convertEntityToDTO(service.update(id, Converter.convertDTOToEntity(purchaseOrderItemDTO, PurchaseOrderItem.class)), PurchaseOrderItemDTO.class);
    }

    @Override
    public void delete(Integer id) {
        service.delete(id);
    }

}
