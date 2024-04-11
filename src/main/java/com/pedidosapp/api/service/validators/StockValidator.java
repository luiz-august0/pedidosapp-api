package com.pedidosapp.api.service.validators;

import com.pedidosapp.api.model.entities.Stock;
import com.pedidosapp.api.service.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.service.validators.classes.GreaterThanOrEqualZeroField;
import com.pedidosapp.api.service.validators.classes.RequiredField;

import java.util.ArrayList;
import java.util.List;

public class StockValidator extends AbstractValidator {
    public StockValidator() {
        try {
            List<RequiredField> requiredFields = new ArrayList<>();
            List<GreaterThanOrEqualZeroField> greaterThanOrEqualZeroFields = new ArrayList<>();
            requiredFields.add(new RequiredField(Stock.class.getDeclaredField("product"), "produto"));
            requiredFields.add(new RequiredField(Stock.class.getDeclaredField("quantity"), "quantidade"));
            requiredFields.add(new RequiredField(Stock.class.getDeclaredField("entry"), "entrada/saida"));
            greaterThanOrEqualZeroFields.add(new GreaterThanOrEqualZeroField(Stock.class.getDeclaredField("quantity"), "quantidade"));

            super.addListOfRequiredFields(requiredFields);
            super.addListOfGreaterThanOrEqualZeroFields(greaterThanOrEqualZeroFields);
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }
}