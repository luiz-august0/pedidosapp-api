package com.pedidosapp.api.service.validators;

import com.pedidosapp.api.model.entities.Order;
import com.pedidosapp.api.model.enums.EnumStatusOrder;
import com.pedidosapp.api.service.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.service.exceptions.enums.EnumUnauthorizedException;
import com.pedidosapp.api.service.validators.classes.RequiredField;
import com.pedidosapp.api.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class OrderValidator extends AbstractValidator {
    public OrderValidator() {
        try {
            List<RequiredField> requiredFields = new ArrayList<>();
            requiredFields.add(new RequiredField(Order.class.getDeclaredField("customer"), "cliente"));
            requiredFields.add(new RequiredField(Order.class.getDeclaredField("user"), "usuário"));
            requiredFields.add(new RequiredField(Order.class.getDeclaredField("amount"), "valor total"));
            requiredFields.add(new RequiredField(Order.class.getDeclaredField("status"), "status"));
            requiredFields.add(new RequiredField(Order.class.getDeclaredField("inclusionDate"), "data de inclusão"));

            super.addListOfRequiredFields(requiredFields);
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }

    public void validate(Order order) {
        super.validate(order);

        if (Utils.isEmpty(order.getItems())) {
            throw new ApplicationGenericsException(EnumUnauthorizedException.EMPTY_ITEMS_ORDER);
        }

        if (EnumStatusOrder.CLOSED.equals(order.getStatus())) {
            throw new ApplicationGenericsException(EnumUnauthorizedException.ORDER_ALREADY_CLOSED);
        }
    }
}