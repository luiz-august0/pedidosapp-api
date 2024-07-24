package com.pedidosapp.api.validators;

import com.pedidosapp.api.infrastructure.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.model.entities.Product;
import com.pedidosapp.api.validators.classes.RequiredField;

import java.util.ArrayList;
import java.util.List;

public class ProductValidator extends AbstractValidator<Product> {
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