package com.pedidosapp.api.service;

import com.pedidosapp.api.model.entities.PurchaseOrder;
import com.pedidosapp.api.model.entities.PurchaseOrderItem;
import com.pedidosapp.api.model.entities.Stock;
import com.pedidosapp.api.model.enums.EnumObservationStock;
import com.pedidosapp.api.model.enums.EnumStatusOrder;
import com.pedidosapp.api.repository.PurchaseOrderRepository;
import com.pedidosapp.api.utils.Utils;
import com.pedidosapp.api.validators.PurchaseOrderValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseOrderService extends AbstractService<PurchaseOrderRepository, PurchaseOrder, PurchaseOrderValidator> {
    private final PurchaseOrderRepository purchaseOrderRepository;

    private final PurchaseOrderValidator purchaseOrderValidator;

    private final PurchaseOrderItemService purchaseOrderItemService;

    private final CustomerService customerService;

    private final UserService userService;

    private final StockService stockService;

    PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, PurchaseOrderItemService purchaseOrderItemService, CustomerService customerService, UserService userService, StockService stockService) {
        super(purchaseOrderRepository, new PurchaseOrder(), new PurchaseOrderValidator());
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderItemService = purchaseOrderItemService;
        this.customerService = customerService;
        this.userService = userService;
        this.stockService = stockService;
        this.purchaseOrderValidator = new PurchaseOrderValidator();
    }

    @Override
    @Transactional
    public PurchaseOrder insert(PurchaseOrder purchaseOrder) {
        PurchaseOrder purchaseOrderManaged = prepareInsert(purchaseOrder);
        purchaseOrderRepository.save(purchaseOrderManaged);

        return purchaseOrderManaged;
    }

    @Transactional
    public PurchaseOrder closePurchaseOrder(Integer id) {
        PurchaseOrder purchaseOrder = this.findAndValidate(id);

        purchaseOrderValidator.validate(purchaseOrder);

        purchaseOrder.setStatus(EnumStatusOrder.CLOSED);

        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        moveStockPurchaseOrderItems(purchaseOrder.getItems());

        return purchaseOrder;
    }

    private PurchaseOrder prepareInsert(PurchaseOrder purchaseOrder) {
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrder.getItems();
        List<PurchaseOrderItem> purchaseOrderItemsManaged = new ArrayList<>();
        purchaseOrder.setInclusionDate(new Date());
        purchaseOrder.setAmount(BigDecimal.ZERO);
        purchaseOrder.setDiscount(BigDecimal.ZERO);
        purchaseOrder.setAddition(BigDecimal.ZERO);
        purchaseOrder.setCustomer(Utils.isNotEmpty(purchaseOrder.getCustomer()) ? customerService.findAndValidateActive(purchaseOrder.getCustomer().getId(), true) : null);
        purchaseOrder.setUser(userService.findAndValidateActive(getUserByContext().getId(), true));
        purchaseOrder.setStatus(EnumStatusOrder.OPEN);

        purchaseOrderValidator.validate(purchaseOrder);

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

    private void moveStockPurchaseOrderItems(List<PurchaseOrderItem> items) {
        items.forEach(item -> {
            Stock stock = new Stock();
            PurchaseOrder purchaseOrder = item.getPurchaseOrder();

            stock.setPurchaseOrder(purchaseOrder);
            stock.setProduct(item.getProduct());
            stock.setEntry(Boolean.TRUE);
            stock.setQuantity(item.getQuantity());
            stock.setObservation(EnumObservationStock.PURCHASE_ORDER.getObservation() + " #" + purchaseOrder.getId());

            stockService.insert(stock);
        });
    }

}