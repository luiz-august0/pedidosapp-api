package com.pedidosapp.api.model.dtos;

import com.pedidosapp.api.model.entities.Order;
import com.pedidosapp.api.model.enums.EnumStatusOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
@EqualsAndHashCode(of = "id", callSuper = false)
public class OrderDTO extends AbstractDTO<Order> {

    private Integer id;

    private CustomerDTO customer;

    private UserDTO user;

    private BigDecimal amount;

    private BigDecimal discount;

    private BigDecimal addition;

    private EnumStatusOrder status;

    private Date inclusionDate;

    private List<OrderItemDTO> items;

}