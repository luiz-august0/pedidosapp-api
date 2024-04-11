package com.pedidosapp.api.model.entities;

import com.pedidosapp.api.model.enums.EnumStatusOrder;
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
@Table(name = "orders")
@EqualsAndHashCode(of = "id", callSuper = false)
public class Order extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderItem> items;

    public BigDecimal calculateAmount() {
        BigDecimal amount = BigDecimal.ZERO;

        for (OrderItem item : getItems()) {
            if (Utils.isNotEmpty(item.getAmount())) {
                amount = amount.add(item.getAmount());
            }
        }

        return amount;
    }

    public BigDecimal calculateDiscount() {
        BigDecimal discount = BigDecimal.ZERO;

        for (OrderItem item : getItems()) {
            if (Utils.isNotEmpty(item.getDiscount())) {
                discount = discount.add(item.getDiscount());
            }
        }

        return discount;
    }

    public BigDecimal calculateAddition() {
        BigDecimal addition = BigDecimal.ZERO;

        for (OrderItem item : getItems()) {
            if (Utils.isNotEmpty(item.getAddition())) {
                addition = addition.add(item.getAddition());
            }
        }


        return addition;
    }


    @Override
    public String getPortugueseClassName() {
        return "pedido";
    }
}