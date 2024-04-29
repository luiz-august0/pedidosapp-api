package com.pedidosapp.api.service;

import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.model.dtos.OrderItemDTO;
import com.pedidosapp.api.model.entities.Order;
import com.pedidosapp.api.model.entities.OrderItem;
import com.pedidosapp.api.model.entities.Product;
import com.pedidosapp.api.repository.OrderItemRepository;
import com.pedidosapp.api.repository.OrderRepository;
import com.pedidosapp.api.service.validators.OrderItemValidator;
import com.pedidosapp.api.service.validators.OrderValidator;
import com.pedidosapp.api.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderItemService extends AbstractService<OrderItemRepository, OrderItem, OrderItemDTO, OrderItemValidator> {

    private final OrderItemRepository orderItemRepository;

    private final OrderItemValidator orderItemValidator;

    private final ProductService productService;

    private final OrderValidator orderValidator;

    private final OrderRepository orderRepository;

    OrderItemService(OrderItemRepository orderItemRepository, ProductService productService, OrderRepository orderRepository) {
        super(orderItemRepository, new OrderItem(), new OrderItemDTO(), new OrderItemValidator());
        this.orderItemRepository = orderItemRepository;
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.orderValidator = new OrderValidator();
        this.orderItemValidator = new OrderItemValidator();
    }

    @Override
    @Transactional
    public ResponseEntity<OrderItemDTO> insert(OrderItem orderItem) {
        prepareInsertOrUpdate(orderItem);
        orderItemValidator.validate(orderItem);

        OrderItem orderItemManaged = orderItemRepository.save(orderItem);
        calculateAndUpdateOrder(orderItemManaged);

        return ResponseEntity.status(HttpStatus.CREATED).body(Converter.convertEntityToDTO(orderItemManaged, OrderItemDTO.class));
    }

    @Override
    @Transactional
    public ResponseEntity<OrderItemDTO> update(Integer id, OrderItem orderItem) {
        OrderItem orderItemOld = super.findAndValidate(id);
        orderItem.setId(orderItemOld.getId());
        orderItem.setProduct(orderItemOld.getProduct());

        orderItemValidator.validate(orderItem);
        prepareInsertOrUpdate(orderItem);

        OrderItem orderItemManaged = orderItemRepository.save(orderItem);
        calculateAndUpdateOrder(orderItemManaged);

        return ResponseEntity.ok().body(Converter.convertEntityToDTO(orderItemManaged, OrderItemDTO.class));
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
        Product product = productService.findAndValidateActive(orderItem.getProduct().getId());

        orderItem.setProduct(product);
        orderItem.setUnitaryValue(product.getUnitaryValue());
        orderItem.setAmount(orderItem.getUnitaryValue().multiply(orderItem.getQuantity()));
        orderItem.setDiscount(Utils.nvl(orderItem.getDiscount(), BigDecimal.ZERO));
        orderItem.setAddition(Utils.nvl(orderItem.getAddition(), BigDecimal.ZERO));
    }

    private void calculateAndUpdateOrder(OrderItem orderItem) {
        Order order = (Order) this.findAndValidateGeneric(orderRepository, new Order().getPortugueseClassName(), orderItem.getOrder().getId());

        order.setAmount(order.calculateAmount());
        order.setDiscount(order.calculateDiscount());
        order.setAddition(order.calculateAddition());

        orderValidator.validate(order);

        orderRepository.save(order);
    }
}