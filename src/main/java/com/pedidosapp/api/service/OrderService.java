package com.pedidosapp.api.service;

import com.pedidosapp.api.infrastructure.converter.Converter;
import com.pedidosapp.api.model.dtos.OrderDTO;
import com.pedidosapp.api.model.dtos.OrderItemDTO;
import com.pedidosapp.api.model.dtos.StockDTO;
import com.pedidosapp.api.model.entities.Order;
import com.pedidosapp.api.model.entities.OrderItem;
import com.pedidosapp.api.model.enums.EnumObservationStock;
import com.pedidosapp.api.model.enums.EnumStatusOrder;
import com.pedidosapp.api.repository.OrderRepository;
import com.pedidosapp.api.service.validators.OrderValidator;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService extends AbstractService<OrderRepository, Order, OrderDTO, OrderValidator> {
    private final OrderRepository orderRepository;

    private final OrderValidator orderValidator;

    private final OrderItemService orderItemService;

    private final CustomerService customerService;

    private final UserService userService;

    private final StockService stockService;

    OrderService(OrderRepository orderRepository, OrderItemService orderItemService, CustomerService customerService, UserService userService, StockService stockService) {
        super(orderRepository, new Order(), new OrderDTO(), new OrderValidator());
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
        this.customerService = customerService;
        this.userService = userService;
        this.stockService = stockService;
        this.orderValidator = new OrderValidator();
    }

    @Override
    @Transactional
    public ResponseEntity<OrderDTO> insert(OrderDTO orderDTO) {
        Order order = Converter.convertDTOToEntity(orderDTO, Order.class);

        Order orderManaged = prepareInsert(order);
        orderRepository.save(orderManaged);

        return ResponseEntity.status(HttpStatus.CREATED).body(Converter.convertEntityToDTO(orderManaged, OrderDTO.class));
    }

    @Transactional
    public ResponseEntity<OrderDTO> closeOrder(Integer id) {
        Order order = this.findAndValidate(id);

        orderValidator.validate(order);

        order.setStatus(EnumStatusOrder.CLOSED);

        order = orderRepository.save(order);

        moveStockOrderItems(order.getItems().stream().map(item -> Converter.convertEntityToDTO(item, OrderItemDTO.class)).toList());

        return ResponseEntity.status(HttpStatus.OK).body(Converter.convertEntityToDTO(order, OrderDTO.class));
    }

    private Order prepareInsert(Order order) {
        List<OrderItem> orderItems = order.getItems();
        List<OrderItem> orderItemsManaged = new ArrayList<>();
        order.setInclusionDate(new Date());
        order.setAmount(BigDecimal.ZERO);
        order.setDiscount(BigDecimal.ZERO);
        order.setAddition(BigDecimal.ZERO);
        order.setCustomer(customerService.findAndValidateActive(order.getCustomer().getId()));
        order.setUser(userService.findAndValidateActive(getUserByContext().getId()));
        order.setStatus(EnumStatusOrder.OPEN);

        orderValidator.validate(order);

        order.setItems(new ArrayList<>());

        Order orderManaged = orderRepository.save(order);

        orderItems.forEach(orderItem -> {
            orderItem.setOrder(orderManaged);
            OrderItem orderItemManged = orderItemService.insertByOrder(orderItem);

            orderItemsManaged.add(orderItemManged);
        });

        orderManaged.setItems(orderItemsManaged);
        orderManaged.setAmount(orderManaged.calculateAmount());
        orderManaged.setDiscount(orderManaged.calculateDiscount());
        orderManaged.setAddition(orderManaged.calculateAddition());

        return orderManaged;
    }

    private void moveStockOrderItems(List<OrderItemDTO> items) {
        items.forEach(item -> {
            StockDTO stock = new StockDTO();
            OrderDTO order = item.getOrder();

            stock.setOrder(order);
            stock.setProduct(item.getProduct());
            stock.setEntry(Boolean.FALSE);
            stock.setQuantity(item.getQuantity());
            stock.setObservation(EnumObservationStock.ORDER.getObservation() + " " + order.getId());

            stockService.insert(stock);
        });
    }
}