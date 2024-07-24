package com.pedidosapp.api.service;

import com.pedidosapp.api.model.entities.Order;
import com.pedidosapp.api.model.entities.OrderItem;
import com.pedidosapp.api.model.entities.Product;
import com.pedidosapp.api.repository.OrderItemRepository;
import com.pedidosapp.api.repository.OrderRepository;
import com.pedidosapp.api.utils.Utils;
import com.pedidosapp.api.validators.OrderItemValidator;
import com.pedidosapp.api.validators.OrderValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderItemService extends AbstractService<OrderItemRepository, OrderItem, OrderItemValidator> {

    private final OrderItemRepository orderItemRepository;

    private final OrderItemValidator orderItemValidator;

    private final ProductService productService;

    private final OrderValidator orderValidator;

    private final OrderRepository orderRepository;

    OrderItemService(OrderItemRepository orderItemRepository, ProductService productService, OrderRepository orderRepository) {
        super(orderItemRepository, new OrderItem(), new OrderItemValidator());
        this.orderItemRepository = orderItemRepository;
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.orderValidator = new OrderValidator();
        this.orderItemValidator = new OrderItemValidator();
    }

    @Override
    @Transactional
    public OrderItem insert(OrderItem orderItem) {
        prepareInsertOrUpdate(orderItem);
        orderItemValidator.validate(orderItem);

        OrderItem orderItemManaged = orderItemRepository.save(orderItem);
        calculateAndUpdateOrder(orderItemManaged);

        return orderItemManaged;
    }

    @Override
    @Transactional
    public OrderItem update(Integer id, OrderItem orderItem) {
        OrderItem orderItemOld = super.findAndValidate(id);
        orderItem.setId(orderItemOld.getId());
        orderItem.setProduct(orderItemOld.getProduct());

        orderItemValidator.validate(orderItem);
        prepareInsertOrUpdate(orderItem);

        OrderItem orderItemManaged = orderItemRepository.save(orderItem);
        calculateAndUpdateOrder(orderItemManaged);

        return orderItemManaged;
    }

    @Transactional
    public void delete(Integer id) {
        OrderItem orderItem = super.findAndValidate(id);
        orderItemRepository.delete(orderItem);

        calculateAndUpdateOrder(orderItem);
    }

    public OrderItem insertByOrder(OrderItem orderItem) {
        prepareInsertOrUpdate(orderItem);
        orderItemValidator.validate(orderItem);

        orderItemRepository.save(orderItem);
        return orderItem;
    }

    private void prepareInsertOrUpdate(OrderItem orderItem) {
        Product product = productService.findAndValidateActive(orderItem.getProduct().getId(), true);

        orderItem.setProduct(product);
        orderItem.setUnitaryValue(product.getUnitaryValue());
        orderItem.setAmount(orderItem.getUnitaryValue().multiply(orderItem.getQuantity()));
        orderItem.setDiscount(Utils.nvl(orderItem.getDiscount(), BigDecimal.ZERO));
        orderItem.setAddition(Utils.nvl(orderItem.getAddition(), BigDecimal.ZERO));
    }

    private void calculateAndUpdateOrder(OrderItem orderItem) {
        Order order = this.findAndValidateGeneric(Order.class, orderItem.getOrder().getId());

        order.setAmount(order.calculateAmount());
        order.setDiscount(order.calculateDiscount());
        order.setAddition(order.calculateAddition());

        orderValidator.validate(order);

        orderRepository.save(order);
    }

}