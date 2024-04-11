package com.pedidosapp.api.model.dtos;

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

    private PurchaseOrderDTO purchaseOrder;

    private OrderDTO order;

    private ProductDTO product;

    private BigDecimal quantity;

    private Boolean entry;

}