package com.pedidosapp.api.service;

import com.pedidosapp.api.converter.Converter;
import com.pedidosapp.api.model.dtos.PurchaseOrderDTO;
import com.pedidosapp.api.model.entities.PurchaseOrder;
import com.pedidosapp.api.model.entities.PurchaseOrderItem;
import com.pedidosapp.api.model.enums.EnumStatusOrder;
import com.pedidosapp.api.repository.PurchaseOrderRepository;
import com.pedidosapp.api.service.validators.PurchaseOrderValidator;
import com.pedidosapp.api.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseOrderService extends AbstractService<PurchaseOrderRepository, PurchaseOrder, PurchaseOrderDTO, PurchaseOrderValidator> {
    private final PurchaseOrderRepository purchaseOrderRepository;

    private final PurchaseOrderValidator purchaseOrderValidator;

    private final PurchaseOrderItemService purchaseOrderItemService;

    private final CustomerService customerService;

    private final UserService userService;

    PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, PurchaseOrderItemService purchaseOrderItemService, CustomerService customerService, UserService userService) {
        super(purchaseOrderRepository, new PurchaseOrder(), new PurchaseOrderDTO(), new PurchaseOrderValidator());
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderItemService = purchaseOrderItemService;
        this.customerService = customerService;
        this.userService = userService;
        this.purchaseOrderValidator = new PurchaseOrderValidator();
    }

    @Override
    @Transactional
    public ResponseEntity<PurchaseOrderDTO> insert(PurchaseOrderDTO purchaseOrderDTO) {
        PurchaseOrder purchaseOrder = Converter.convertDTOToEntity(purchaseOrderDTO, PurchaseOrder.class);

        PurchaseOrder purchaseOrderManaged = prepareInsert(purchaseOrder);
        purchaseOrderRepository.save(purchaseOrderManaged);

        return ResponseEntity.status(HttpStatus.CREATED).body(Converter.convertEntityToDTO(purchaseOrderManaged, PurchaseOrderDTO.class));
    }

    @Transactional
    public ResponseEntity<PurchaseOrderDTO> closePurchaseOrder(Integer id) {
        PurchaseOrder purchaseOrder = this.findAndValidate(id);
        purchaseOrder.setStatus(EnumStatusOrder.CLOSED);

        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        return ResponseEntity.status(HttpStatus.OK).body(Converter.convertEntityToDTO(purchaseOrder, PurchaseOrderDTO.class));
    }

    private PurchaseOrder prepareInsert(PurchaseOrder purchaseOrder) {
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrder.getItems();
        List<PurchaseOrderItem> purchaseOrderItemsManaged = new ArrayList<>();
        purchaseOrder.setInclusionDate(new Date());
        purchaseOrder.setAmount(BigDecimal.ZERO);
        purchaseOrder.setDiscount(BigDecimal.ZERO);
        purchaseOrder.setAddition(BigDecimal.ZERO);
        purchaseOrder.setCustomer(Utils.isNotEmpty(purchaseOrder.getCustomer())?customerService.findAndValidateActive(purchaseOrder.getCustomer().getId()):null);
        purchaseOrder.setUser(userService.findAndValidateActive(getUserByContext().getId()));

        purchaseOrderValidator.validate(purchaseOrder);

        purchaseOrder.setStatus(EnumStatusOrder.OPEN);
        purchaseOrder.setItems(new ArrayList<>());

        PurchaseOrder purchaseOrderManaged = purchaseOrderRepository.save(purchaseOrder);

        purchaseOrderItems.forEach(purchaseOrderItem -> {
            purchaseOrderItem.setPurchaseOrder(purchaseOrderManaged);
            PurchaseOrderItem purchaseOrderItemManged = purchaseOrderItemService.insertByPurchaseOrder(purchaseOrderItem);

            purchaseOrderItemsManaged.add(purchaseOrderItemManged);
        });

        purchaseOrderManaged.setItems(purchaseOrderItemsManaged);
        purchaseOrderManaged.setAmount(purchaseOrderManaged.calculateAmount());
        purchaseOrderManaged.setDiscount(purchaseOrderManaged.calculateDiscount());
        purchaseOrderManaged.setAddition(purchaseOrderManaged.calculateAddition());

        return purchaseOrderManaged;
    }
}