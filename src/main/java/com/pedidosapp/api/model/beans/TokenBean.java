package com.pedidosapp.api.model.beans;

import com.pedidosapp.api.model.dtos.EmployeeDTO;
import com.pedidosapp.api.model.enums.EnumUserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenBean {
    private Integer userId;

    private String login;

    private EnumUserRole role;

    private EmployeeDTO employee;

    private String accessToken;

    private String refreshToken;
}