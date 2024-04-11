package com.pedidosapp.api.service.validators;

import com.pedidosapp.api.model.entities.Supplier;
import com.pedidosapp.api.service.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.service.validators.classes.CharacterLengthField;
import com.pedidosapp.api.service.validators.classes.RequiredField;

import java.util.ArrayList;
import java.util.List;

public class SupplierValidator extends AbstractValidator {
    public SupplierValidator() {
        try {
            List<RequiredField> requiredFields = new ArrayList<>();
            List<CharacterLengthField> characterLengthFields = new ArrayList<>();
            requiredFields.add(new RequiredField(Supplier.class.getDeclaredField("name"), "nome"));
            characterLengthFields.add(new CharacterLengthField(Supplier.class.getDeclaredField("name"), 3, false, "nome"));
            characterLengthFields.add(new CharacterLengthField(Supplier.class.getDeclaredField("name"), 255, true, "nome"));

            super.addListOfRequiredFields(requiredFields);
            super.addListOfCharacterLengthFields(characterLengthFields);
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }
}