package com.pedidosapp.api.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pedidosapp.api.infrastructure.annotations.ObjectFieldsOnly;
import com.pedidosapp.api.model.entities.Stock;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
public class StockDTO extends AbstractDTO<Stock> {

    private Integer id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ObjectFieldsOnly({"id"})
    private PurchaseOrderDTO purchaseOrder;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ObjectFieldsOnly({"id"})
    private OrderDTO order;

    private ProductDTO product;

    private BigDecimal quantity;

    private Boolean entry;

    private String observation;

}