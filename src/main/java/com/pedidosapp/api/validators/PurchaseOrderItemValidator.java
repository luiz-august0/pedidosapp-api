package com.pedidosapp.api.validators;

import com.pedidosapp.api.infrastructure.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.model.entities.PurchaseOrderItem;
import com.pedidosapp.api.validators.classes.RequiredField;

import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderItemValidator extends AbstractValidator<PurchaseOrderItem> {
    public PurchaseOrderItemValidator() {
        try {
            List<RequiredField> requiredFields = new ArrayList<>();
            requiredFields.add(new RequiredField(PurchaseOrderItem.class.getDeclaredField("purchaseOrder"), "pedido de compra"));
            requiredFields.add(new RequiredField(PurchaseOrderItem.class.getDeclaredField("product"), "produto"));
            requiredFields.add(new RequiredField(PurchaseOrderItem.class.getDeclaredField("quantity"), "quantidade"));
            requiredFields.add(new RequiredField(PurchaseOrderItem.class.getDeclaredField("unitaryValue"), "valor unitário"));
            requiredFields.add(new RequiredField(PurchaseOrderItem.class.getDeclaredField("amount"), "valor total"));

            super.addListOfRequiredFields(requiredFields);
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }
}