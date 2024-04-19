package com.pedidosapp.api.service.validators;

import com.pedidosapp.api.model.records.RegisterRecord;
import com.pedidosapp.api.repository.UserRepository;
import com.pedidosapp.api.service.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.service.exceptions.enums.EnumUnauthorizedException;
import com.pedidosapp.api.service.validators.classes.RequiredField;

import java.util.ArrayList;
import java.util.List;

public class UserValidator extends AbstractValidator {
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;

        try {
            List<RequiredField> requiredFields = new ArrayList<>();
            requiredFields.add(new RequiredField(RegisterRecord.class.getDeclaredField("login"), "login"));
            requiredFields.add(new RequiredField(RegisterRecord.class.getDeclaredField("password"), "senha"));
            requiredFields.add(new RequiredField(RegisterRecord.class.getDeclaredField("role"), "nivel de acesso"));

            super.addListOfRequiredFields(requiredFields);
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }

    @Override
    public void validate(Object object) {
        RegisterRecord registerRecord = (RegisterRecord) object;
        super.validate(object);

        if (userRepository.findByLogin(registerRecord.login()) != null)
            throw new ApplicationGenericsException(EnumUnauthorizedException.USER_ALREADY_REGISTERED);
    }
}