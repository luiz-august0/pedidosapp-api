package com.pedidosapp.api.model.dtos;

import com.pedidosapp.api.model.entities.Product;
import com.pedidosapp.api.model.enums.EnumUnitProduct;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class ProductDTO extends AbstractDTO<Product> {
    private Integer id;

    private String description;

    private EnumUnitProduct unit;

    private BigDecimal unitaryValue;

    private List<ProductSupplierDTO> productSuppliers;

    private Boolean active;
}