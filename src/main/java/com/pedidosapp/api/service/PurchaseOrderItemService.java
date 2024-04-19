package com.pedidosapp.api.service;

import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.model.dtos.PurchaseOrderItemDTO;
import com.pedidosapp.api.model.entities.Product;
import com.pedidosapp.api.model.entities.PurchaseOrder;
import com.pedidosapp.api.model.entities.PurchaseOrderItem;
import com.pedidosapp.api.repository.PurchaseOrderItemRepository;
import com.pedidosapp.api.repository.PurchaseOrderRepository;
import com.pedidosapp.api.service.validators.PurchaseOrderItemValidator;
import com.pedidosapp.api.service.validators.PurchaseOrderValidator;
import com.pedidosapp.api.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PurchaseOrderItemService extends AbstractService<PurchaseOrderItemRepository, PurchaseOrderItem, PurchaseOrderItemDTO, PurchaseOrderItemValidator> {

    private final PurchaseOrderItemRepository purchaseOrderItemRepository;

    private final PurchaseOrderItemValidator purchaseOrderItemValidator;

    private final ProductService productService;

    private final PurchaseOrderValidator purchaseOrderValidator;

    private final PurchaseOrderRepository purchaseOrderRepository;

    PurchaseOrderItemService(PurchaseOrderItemRepository purchaseOrderItemRepository, ProductService productService, PurchaseOrderRepository purchaseOrderRepository) {
        super(purchaseOrderItemRepository, new PurchaseOrderItem(), new PurchaseOrderItemDTO(), new PurchaseOrderItemValidator());
        this.purchaseOrderItemRepository = purchaseOrderItemRepository;
        this.productService = productService;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderValidator = new PurchaseOrderValidator();
        this.purchaseOrderItemValidator = new PurchaseOrderItemValidator();
    }

    @Override
    @Transactional
    public ResponseEntity<PurchaseOrderItemDTO> insert(PurchaseOrderItemDTO purchaseOrderItemDTO) {
        PurchaseOrderItem purchaseOrderItem = Converter.convertDTOToEntity(purchaseOrderItemDTO, PurchaseOrderItem.class);
        prepareInsertOrUpdate(purchaseOrderItem);
        purchaseOrderItemValidator.validate(purchaseOrderItem);

        PurchaseOrderItem purchaseOrderItemManaged = purchaseOrderItemRepository.save(purchaseOrderItem);
        calculateAndUpdatePurchaseOrder(purchaseOrderItemManaged);

        return ResponseEntity.status(HttpStatus.CREATED).body(Converter.convertEntityToDTO(purchaseOrderItemManaged, PurchaseOrderItemDTO.class));
    }

    @Override
    @Transactional
    public ResponseEntity<PurchaseOrderItemDTO> update(Integer id, PurchaseOrderItemDTO purchaseOrderItemDTO) {
        PurchaseOrderItem purchaseOrderItemOld = super.findAndValidate(id);
        PurchaseOrderItem purchaseOrderItem = Converter.convertDTOToEntity(purchaseOrderItemDTO, PurchaseOrderItem.class);
        purchaseOrderItem.setId(purchaseOrderItemOld.getId());
        purchaseOrderItem.setProduct(purchaseOrderItemOld.getProduct());

        purchaseOrderItemValidator.validate(purchaseOrderItem);
        prepareInsertOrUpdate(purchaseOrderItem);

        PurchaseOrderItem purchaseOrderItemManaged = purchaseOrderItemRepository.save(purchaseOrderItem);
        calculateAndUpdatePurchaseOrder(purchaseOrderItemManaged);

        return ResponseEntity.ok().body(Converter.convertEntityToDTO(purchaseOrderItemManaged, PurchaseOrderItemDTO.class));
    }

    @Transactional
    public void delete(Integer id) {
        PurchaseOrderItem purchaseOrderItem = super.findAndValidate(id);
        purchaseOrderItemRepository.delete(purchaseOrderItem);

        calculateAndUpdatePurchaseOrder(purchaseOrderItem);
    }

    public PurchaseOrderItem insertByPurchaseOrder(PurchaseOrderItem purchaseOrderItem) {
        prepareInsertOrUpdate(purchaseOrderItem);
        purchaseOrderItemValidator.validate(purchaseOrderItem);

        purchaseOrderItemRepository.save(purchaseOrderItem);
        return purchaseOrderItem;
    }

    private void prepareInsertOrUpdate(PurchaseOrderItem purchaseOrderItem) {
        Product product = productService.findAndValidateActive(purchaseOrderItem.getProduct().getId());

        purchaseOrderItem.setProduct(product);
        purchaseOrderItem.setUnitaryValue(product.getUnitaryValue());
        purchaseOrderItem.setAmount(purchaseOrderItem.getUnitaryValue().multiply(purchaseOrderItem.getQuantity()));
        purchaseOrderItem.setDiscount(Utils.nvl(purchaseOrderItem.getDiscount(), BigDecimal.ZERO));
        purchaseOrderItem.setAddition(Utils.nvl(purchaseOrderItem.getAddition(), BigDecimal.ZERO));
    }

    private void calculateAndUpdatePurchaseOrder(PurchaseOrderItem purchaseOrderItem) {
        PurchaseOrder purchaseOrder = (PurchaseOrder) this.findAndValidateGeneric(purchaseOrderRepository, new PurchaseOrder().getPortugueseClassName(), purchaseOrderItem.getPurchaseOrder().getId());

        purchaseOrder.setAmount(purchaseOrder.calculateAmount());
        purchaseOrder.setDiscount(purchaseOrder.calculateDiscount());
        purchaseOrder.setAddition(purchaseOrder.calculateAddition());

        purchaseOrderValidator.validate(purchaseOrder);

        purchaseOrderRepository.save(purchaseOrder);
    }
}