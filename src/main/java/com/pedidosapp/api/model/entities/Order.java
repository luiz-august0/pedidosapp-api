package com.pedidosapp.api.model.entities;

import com.pedidosapp.api.model.enums.EnumStatusOrder;
import com.pedidosapp.api.service.AbstractService;
import com.pedidosapp.api.service.OrderService;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Entity
@Data
@Table(name = "orders")
@EqualsAndHashCode(of = "id", callSuper = false)
public class Order extends AbstractEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "id_order", sequenceName = "gen_id_order", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_order")
    private Integer id;

    @JoinColumn(name = "customer_id", nullable = false)
    @ManyToOne
    private Customer customer;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private User user;

    @Column(nullable = false, name = "amount")
    private BigDecimal amount;

    @Column(nullable = false, name = "discount")
    private BigDecimal discount;

    @Column(nullable = false, name = "addition")
    private BigDecimal addition;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private EnumStatusOrder status;

    @Column(nullable = false, name = "inclusion_date")
    private Date inclusionDate;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<OrderItem> items;

    public BigDecimal calculateAmount() {
        return getItems().stream()
                .map(OrderItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateDiscount() {
        return getItems().stream()
                .map(OrderItem::getDiscount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateAddition() {
        return getItems().stream()
                .map(OrderItem::getAddition)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String getPortugueseClassName() {
        return "pedido";
    }

    @Override
    public Class<? extends AbstractService> getServiceClass() {
        return OrderService.class;
    }

}