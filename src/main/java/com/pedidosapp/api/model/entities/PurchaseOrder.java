package com.pedidosapp.api.model.entities;

import com.pedidosapp.api.model.enums.EnumStatusOrder;
import com.pedidosapp.api.service.AbstractService;
import com.pedidosapp.api.service.PurchaseOrderService;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Entity
@Data
@Table(name = "purchase_order")
@EqualsAndHashCode(of = "id", callSuper = false)
public class PurchaseOrder extends AbstractEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "id_purchase_order", sequenceName = "gen_id_purchase_order", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_purchase_order")
    private Integer id;

    @JoinColumn(name = "customer_id")
    @ManyToOne
    private Customer customer;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private User user;

    @JoinColumn(name = "order_id")
    @ManyToOne
    private Order order;

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

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL)
    List<PurchaseOrderItem> items;

    public BigDecimal calculateAmount() {
        return getItems().stream()
                .map(PurchaseOrderItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateDiscount() {
        return getItems().stream()
                .map(PurchaseOrderItem::getDiscount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateAddition() {
        return getItems().stream()
                .map(PurchaseOrderItem::getAddition)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String getPortugueseClassName() {
        return "pedido de compra";
    }

    @Override
    public Class<? extends AbstractService> getServiceClass() {
        return PurchaseOrderService.class;
    }

}