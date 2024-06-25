package com.pedidosapp.api.model.entities;

import com.pedidosapp.api.service.AbstractService;
import com.pedidosapp.api.service.PurchaseOrderItemService;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;


@Entity
@Data
@Table(name = "purchase_order_item")
@EqualsAndHashCode(of = "id", callSuper = false)
public class PurchaseOrderItem extends AbstractEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "id_purchase_order_item", sequenceName = "gen_id_purchase_order_item", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_purchase_order_item")
    private Integer id;

    @JoinColumn(name = "purchase_order_id", nullable = false)
    @ManyToOne
    private PurchaseOrder purchaseOrder;

    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne
    private Product product;

    @Column(nullable = false, name = "quantity")
    private BigDecimal quantity;

    @Column(nullable = false, name = "unitary_value")
    private BigDecimal unitaryValue;

    @Column(nullable = false, name = "amount")
    private BigDecimal amount;

    @Column(nullable = false, name = "discount")
    private BigDecimal discount;

    @Column(nullable = false, name = "addition")
    private BigDecimal addition;

    @Override
    public String getPortugueseClassName() {
        return "item do pedido de compra";
    }

    @Override
    public Class<? extends AbstractService> getServiceClass() {
        return PurchaseOrderItemService.class;
    }

}