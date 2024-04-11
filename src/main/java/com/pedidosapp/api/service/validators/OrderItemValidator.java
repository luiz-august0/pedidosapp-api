package com.pedidosapp.api.service.validators;

import com.pedidosapp.api.model.entities.OrderItem;
import com.pedidosapp.api.service.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.service.validators.classes.RequiredField;

import java.util.ArrayList;
import java.util.List;

public class OrderItemValidator extends AbstractValidator {
    public OrderItemValidator() {
        try {
            List<RequiredField> requiredFields = new ArrayList<>();
            requiredFields.add(new RequiredField(OrderItem.class.getDeclaredField("order"), "pedido"));
            requiredFields.add(new RequiredField(OrderItem.class.getDeclaredField("product"), "produto"));
            requiredFields.add(new RequiredField(OrderItem.class.getDeclaredField("quantity"), "quantidade"));
            requiredFields.add(new RequiredField(OrderItem.class.getDeclaredField("unitaryValue"), "valor unitário"));
            requiredFields.add(new RequiredField(OrderItem.class.getDeclaredField("amount"), "valor total"));

            super.addListOfRequiredFields(requiredFields);
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }
}