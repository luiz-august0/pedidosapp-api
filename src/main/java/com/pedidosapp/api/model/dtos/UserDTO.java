package com.pedidosapp.api.model.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pedidosapp.api.model.entities.User;
import com.pedidosapp.api.model.enums.EnumUserRole;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class UserDTO extends AbstractDTO<User> {
    private Integer id;

    private String login;

    @JsonIgnore
    private String password;

    private EnumUserRole role;

    private Boolean active;
}