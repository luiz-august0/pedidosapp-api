package com.pedidosapp.api.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pedidosapp.api.model.entities.PurchaseOrderItem;
import lombok.*;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class PurchaseOrderItemDTO extends AbstractDTO<PurchaseOrderItem> {
    private Integer id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private PurchaseOrderDTO purchaseOrder;

    private ProductDTO product;

    private BigDecimal quantity;

    private BigDecimal unitaryValue;

    private BigDecimal amount;

    private BigDecimal discount;

    private BigDecimal addition;
}