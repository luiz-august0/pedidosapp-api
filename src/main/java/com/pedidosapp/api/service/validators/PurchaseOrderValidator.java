package com.pedidosapp.api.service.validators;

import com.pedidosapp.api.model.entities.PurchaseOrder;
import com.pedidosapp.api.model.enums.EnumStatusOrder;
import com.pedidosapp.api.service.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.service.exceptions.enums.EnumUnauthorizedException;
import com.pedidosapp.api.service.validators.classes.RequiredField;
import com.pedidosapp.api.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderValidator extends AbstractValidator {
    public PurchaseOrderValidator() {
        try {
            List<RequiredField> requiredFields = new ArrayList<>();
            requiredFields.add(new RequiredField(PurchaseOrder.class.getDeclaredField("user"), "usuário"));
            requiredFields.add(new RequiredField(PurchaseOrder.class.getDeclaredField("amount"), "valor total"));
            requiredFields.add(new RequiredField(PurchaseOrder.class.getDeclaredField("status"), "status"));
            requiredFields.add(new RequiredField(PurchaseOrder.class.getDeclaredField("inclusionDate"), "data de inclusão"));

            super.addListOfRequiredFields(requiredFields);
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }

    public void validate(PurchaseOrder purchaseOrder) {
        super.validate(purchaseOrder);

        if (Utils.isEmpty(purchaseOrder.getItems())) {
            throw new ApplicationGenericsException(EnumUnauthorizedException.EMPTY_ITEMS_ORDER);
        }

        if (EnumStatusOrder.CLOSED.equals(purchaseOrder.getStatus())) {
            throw new ApplicationGenericsException(EnumUnauthorizedException.ORDER_ALREADY_CLOSED);
        }
    }
}