package com.pedidosapp.api.service.validators;

import com.pedidosapp.api.model.entities.User;
import com.pedidosapp.api.repository.UserRepository;
import com.pedidosapp.api.service.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.service.exceptions.enums.EnumUnauthorizedException;
import com.pedidosapp.api.service.validators.classes.RequiredField;
import com.pedidosapp.api.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class UserValidator extends AbstractValidator {
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;

        try {
            List<RequiredField> requiredFields = new ArrayList<>();
            requiredFields.add(new RequiredField(User.class.getDeclaredField("login"), "login"));
            requiredFields.add(new RequiredField(User.class.getDeclaredField("password"), "senha"));
            requiredFields.add(new RequiredField(User.class.getDeclaredField("role"), "nivel de acesso"));

            super.addListOfRequiredFields(requiredFields);
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }

    @Override
    public void validate(Object object) {
        User user = (User) object;
        super.validate(object);

        if (Utils.isEmpty(user.getId())) {
            if (userRepository.findByLogin(user.getLogin()) != null)
                throw new ApplicationGenericsException(EnumUnauthorizedException.USER_ALREADY_REGISTERED);
        } else {
            if (userRepository.findByLoginAndIdIsNot(user.getLogin(), user.getId()) != null)
                throw new ApplicationGenericsException(EnumUnauthorizedException.USER_ALREADY_REGISTERED);
        }
    }
}