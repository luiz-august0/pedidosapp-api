package com.pedidosapp.api.model.dtos;

import com.pedidosapp.api.model.entities.PurchaseOrder;
import com.pedidosapp.api.model.enums.EnumStatusOrder;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class PurchaseOrderDTO extends AbstractDTO<PurchaseOrder> {
    private Integer id;

    private CustomerDTO customer;

    private OrderDTO order;

    private UserDTO user;

    private BigDecimal amount;

    private BigDecimal discount;

    private BigDecimal addition;

    private EnumStatusOrder status;

    private Date inclusionDate;

    private List<PurchaseOrderItemDTO> items;

}