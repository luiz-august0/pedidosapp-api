package com.pedidosapp.api.model.dtos;

import com.pedidosapp.api.model.entities.Supplier;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class SupplierDTO extends AbstractDTO<Supplier> {
    private Integer id;

    private String name;

    private String socialReason;

    private String cnpj;

    private String cpf;

    private String contact;

    private Boolean active;
}