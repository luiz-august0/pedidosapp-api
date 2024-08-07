package com.pedidosapp.api.model.dtos;

import com.pedidosapp.api.infrastructure.annotations.ObjectFieldsOnly;
import com.pedidosapp.api.model.entities.Product;
import com.pedidosapp.api.model.enums.EnumUnitProduct;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
public class ProductDTO extends AbstractDTO<Product> {

    private Integer id;

    private String description;

    private EnumUnitProduct unit;

    private BigDecimal unitaryValue;

    @ObjectFieldsOnly(ignored = {"products"})
    private List<SupplierDTO> suppliers;

    private Boolean active;

}