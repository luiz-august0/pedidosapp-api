package com.pedidosapp.api.model.beans;

import lombok.Data;

@Data
public class EmployeeBean {
    private String login;

    private String password;

    private String name;

    private String cpf;

    private String contact;
}