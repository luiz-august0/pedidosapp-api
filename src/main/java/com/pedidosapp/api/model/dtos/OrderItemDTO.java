package com.pedidosapp.api.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pedidosapp.api.infrastructure.annotations.ObjectFieldsOnly;
import com.pedidosapp.api.model.entities.OrderItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;


@Data
@EqualsAndHashCode(of = "id", callSuper = false)
public class OrderItemDTO extends AbstractDTO<OrderItem> {

    private Integer id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private OrderDTO order;

    @ObjectFieldsOnly({"id", "description", "unit", "unitaryValue"})
    private ProductDTO product;

    private BigDecimal quantity;

    private BigDecimal unitaryValue;

    private BigDecimal amount;

    private BigDecimal discount;

    private BigDecimal addition;

}