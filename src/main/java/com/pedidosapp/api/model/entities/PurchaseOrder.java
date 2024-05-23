package com.pedidosapp.api.model.entities;

import com.pedidosapp.api.model.enums.EnumStatusOrder;
import com.pedidosapp.api.service.AbstractService;
import com.pedidosapp.api.service.PurchaseOrderService;
import com.pedidosapp.api.utils.Utils;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
        BigDecimal amount = BigDecimal.ZERO;

        for (PurchaseOrderItem item : getItems()) {
            if (Utils.isNotEmpty(item.getAmount())) {
                amount = amount.add(item.getAmount());
            }
        }

        return amount;
    }

    public BigDecimal calculateDiscount() {
        BigDecimal discount = BigDecimal.ZERO;

        for (PurchaseOrderItem item : getItems()) {
            if (Utils.isNotEmpty(item.getDiscount())) {
                discount = discount.add(item.getDiscount());
            }
        }

        return discount;
    }

    public BigDecimal calculateAddition() {
        BigDecimal addition = BigDecimal.ZERO;

        for (PurchaseOrderItem item : getItems()) {
            if (Utils.isNotEmpty(item.getAddition())) {
                addition = addition.add(item.getAddition());
            }
        }


        return addition;
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