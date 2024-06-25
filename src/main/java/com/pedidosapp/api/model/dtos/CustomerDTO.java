package com.pedidosapp.api.model.dtos;

import com.pedidosapp.api.model.entities.Customer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
public class CustomerDTO extends AbstractDTO<Customer> {

    private Integer id;

    private String name;

    private String email;

    private String cnpj;

    private String cpf;

    private String contact;

    private Boolean active;

}