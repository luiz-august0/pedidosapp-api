package com.pedidosapp.api.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pedidosapp.api.model.entities.OrderItem;
import lombok.*;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class OrderItemDTO extends AbstractDTO<OrderItem> {
    private Integer id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private OrderDTO order;

    private ProductDTO product;

    private BigDecimal quantity;

    private BigDecimal unitaryValue;

    private BigDecimal amount;

    private BigDecimal discount;

    private BigDecimal addition;
}