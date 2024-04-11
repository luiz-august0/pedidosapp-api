package com.pedidosapp.api.service.validators;

import com.pedidosapp.api.model.entities.Product;
import com.pedidosapp.api.service.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.service.validators.classes.RequiredField;

import java.util.ArrayList;
import java.util.List;

public class ProductValidator extends AbstractValidator {
    public ProductValidator() {
        try {
            List<RequiredField> requiredFields = new ArrayList<>();
            requiredFields.add(new RequiredField(Product.class.getDeclaredField("description"), "descrição"));
            requiredFields.add(new RequiredField(Product.class.getDeclaredField("unit"), "unidade"));
            requiredFields.add(new RequiredField(Product.class.getDeclaredField("unitaryValue"), "valor unitário"));

            super.addListOfRequiredFields(requiredFields);
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }
}