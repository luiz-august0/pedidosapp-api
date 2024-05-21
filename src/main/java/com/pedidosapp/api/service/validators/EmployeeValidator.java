package com.pedidosapp.api.service.validators;

import com.pedidosapp.api.model.entities.Employee;
import com.pedidosapp.api.repository.EmployeeRepository;
import com.pedidosapp.api.service.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.service.exceptions.enums.EnumUnauthorizedException;
import com.pedidosapp.api.service.validators.classes.CharacterLengthField;
import com.pedidosapp.api.service.validators.classes.RequiredField;
import com.pedidosapp.api.utils.CpfUtil;
import com.pedidosapp.api.utils.StringUtil;
import com.pedidosapp.api.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class EmployeeValidator extends AbstractValidator {

    private final EmployeeRepository employeeRepository;

    public EmployeeValidator(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;

        try {
            List<RequiredField> requiredFields = new ArrayList<>();
            List<CharacterLengthField> characterLengthFields = new ArrayList<>();
            requiredFields.add(new RequiredField(Employee.class.getDeclaredField("name"), "nome"));
            requiredFields.add(new RequiredField(Employee.class.getDeclaredField("user"), "usuário"));
            characterLengthFields.add(new CharacterLengthField(Employee.class.getDeclaredField("name"), 3, false, "nome"));
            characterLengthFields.add(new CharacterLengthField(Employee.class.getDeclaredField("name"), 255, true, "nome"));

            super.addListOfRequiredFields(requiredFields);
            super.addListOfCharacterLengthFields(characterLengthFields);
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }

    @Override
    public void validate(Object object) {
        Employee employee = (Employee) object;
        String cpf = employee.getCpf();

        super.validate(object);

        if (StringUtil.isNotNullOrEmpty(cpf)) {
            CpfUtil.validate(cpf);

            if (employeeRepository.existsByCpfAndIdIsNot(cpf, Utils.nvl(employee.getId(), 0)))
                throw new ApplicationGenericsException(EnumUnauthorizedException.CPF_ALREADY_REGISTERED);
        }
    }
}