package com.pedidosapp.api.model.dtos;

import com.pedidosapp.api.infrastructure.annotations.ObjectFieldsOnly;
import com.pedidosapp.api.model.entities.Supplier;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class SupplierDTO extends AbstractDTO<Supplier> {
    private Integer id;

    private String name;

    private String email;

    private String socialReason;

    private String cnpj;

    private String cpf;

    private String contact;

    private Boolean active;

    @ObjectFieldsOnly(ignored = {"suppliers"})
    private List<ProductDTO> products;
}