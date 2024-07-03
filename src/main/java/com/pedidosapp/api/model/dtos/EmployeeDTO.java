package com.pedidosapp.api.model.dtos;

import com.pedidosapp.api.model.entities.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
public class EmployeeDTO extends AbstractDTO<Employee> {

    private Integer id;

    private String name;

    private String email;

    private String cpf;

    private String contact;

    private UserDTO user;

    private Boolean active;

}