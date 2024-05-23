package com.pedidosapp.api.model.entities;

import com.pedidosapp.api.service.AbstractService;
import com.pedidosapp.api.service.StockService;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "stock")
@EqualsAndHashCode(of = "id", callSuper = false)
public class Stock extends AbstractEntity {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "id_stock", sequenceName = "gen_id_stock", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_stock")
    private Integer id;

    @JoinColumn(name = "purchase_order_id")
    @ManyToOne
    private PurchaseOrder purchaseOrder;

    @JoinColumn(name = "order_id")
    @ManyToOne
    private Order order;

    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne
    private Product product;

    @Column(nullable = false, name = "quantity")
    private BigDecimal quantity;

    @Column(nullable = false, name = "entry")
    private Boolean entry;

    @Column(nullable = false, name = "observation", length = 150)
    private String observation;

    @Override
    public String getPortugueseClassName() {
        return "estoque";
    }

    @Override
    public Class<? extends AbstractService> getServiceClass() {
        return StockService.class;
    }
}