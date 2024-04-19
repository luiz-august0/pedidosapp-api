package com.pedidosapp.api.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pedidosapp.api.model.entities.Stock;
import lombok.*;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class StockDTO extends AbstractDTO<Stock> {

    private Integer id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private PurchaseOrderDTO purchaseOrder;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OrderDTO order;

    private ProductDTO product;

    private BigDecimal quantity;

    private Boolean entry;

    private String observation;

}