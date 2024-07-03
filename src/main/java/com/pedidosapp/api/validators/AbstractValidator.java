package com.pedidosapp.api.validators;

import com.pedidosapp.api.infrastructure.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.infrastructure.exceptions.ValidatorException;
import com.pedidosapp.api.utils.NumericUtil;
import com.pedidosapp.api.utils.Utils;
import com.pedidosapp.api.validators.classes.CharacterLengthField;
import com.pedidosapp.api.validators.classes.GreaterThanOrEqualZeroField;
import com.pedidosapp.api.validators.classes.RequiredField;

import java.util.List;

public abstract class AbstractValidator {
    private List<RequiredField> requiredFields;

    private List<CharacterLengthField> characterLengthFields;

    private List<GreaterThanOrEqualZeroField> greaterThanOrEqualZeroFields;

    public void validate(Object object) {
        validateRequiredFields(object);
        validateCharacterLengthFields(object);
    };

    public void validateRequiredFields(Object object) {
        if (Utils.isNotEmpty(requiredFields)){
            requiredFields.forEach(requiredField -> {
                requiredField.getField().setAccessible(true);

                try {
                    if (Utils.isEmpty(requiredField.getField().get(object))) {
                        throw new ValidatorException(ValidatorException.mountMessageToRequiredField(requiredField.getPortugueseField()));
                    }
                } catch (IllegalAccessException e) {
                    throw new ApplicationGenericsException(e.getMessage());
                }
            });
        }
    }

    public void validateCharacterLengthFields(Object object) {
        if (Utils.isNotEmpty(characterLengthFields)) {
            characterLengthFields.forEach(characterLengthField -> {
                characterLengthField.getField().setAccessible(true);

                try {
                    if (characterLengthField.getField().getType().equals(String.class)) {
                        if (characterLengthField.getMax()) {
                            if (characterLengthField.getField().get(object).toString().length() > characterLengthField.getValue()) {
                                throw new ValidatorException(ValidatorException.mountMessageToCharacterLengthField(
                                        characterLengthField.getPortugueseField(),
                                        characterLengthField.getValue(),
                                        characterLengthField.getMax()
                                ));
                            }
                        } else {
                            if (characterLengthField.getField().get(object).toString().length() < characterLengthField.getValue()) {
                                throw new ValidatorException(ValidatorException.mountMessageToCharacterLengthField(
                                        characterLengthField.getPortugueseField(),
                                        characterLengthField.getValue(),
                                        characterLengthField.getMax()
                                ));
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new ApplicationGenericsException(e.getMessage());
                }
            });
        }
    }

    public void validateGreaterThanOrEqualZeroFields(Object object) {
        if (Utils.isNotEmpty(greaterThanOrEqualZeroFields)) {
            greaterThanOrEqualZeroFields.forEach(greaterThanOrEqualZeroField -> {
                greaterThanOrEqualZeroField.getField().setAccessible(true);

                try {
                    if (NumericUtil.isLessThanZero((Number) greaterThanOrEqualZeroField.getField().get(object))) {
                        throw new ValidatorException(ValidatorException.mountMessageToGreaterThanOrEqualZeroField(greaterThanOrEqualZeroField.getPortugueseField()));
                    }
                } catch (IllegalAccessException e) {
                    throw new ApplicationGenericsException(e.getMessage());
                }
            });
        }
    }

    public void addListOfRequiredFields(List<RequiredField> fields) {
        requiredFields = fields;
    };

    public void addListOfCharacterLengthFields(List<CharacterLengthField> fields) { characterLengthFields = fields; };

    public void addListOfGreaterThanOrEqualZeroFields(List<GreaterThanOrEqualZeroField> fields) { greaterThanOrEqualZeroFields = fields; };

}