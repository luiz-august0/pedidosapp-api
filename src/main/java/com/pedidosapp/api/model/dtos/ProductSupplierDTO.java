package com.pedidosapp.api.model.dtos;

import com.pedidosapp.api.model.entities.ProductSupplier;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class ProductSupplierDTO extends AbstractDTO<ProductSupplier> {
    private Integer id;

    private SupplierDTO supplier;
}