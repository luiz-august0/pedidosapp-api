package com.pedidosapp.api.service.validators;

import com.pedidosapp.api.model.entities.Customer;
import com.pedidosapp.api.repository.CustomerRepository;
import com.pedidosapp.api.service.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.service.exceptions.enums.EnumUnauthorizedException;
import com.pedidosapp.api.service.validators.classes.CharacterLengthField;
import com.pedidosapp.api.service.validators.classes.RequiredField;
import com.pedidosapp.api.utils.CnpjUtil;
import com.pedidosapp.api.utils.CpfUtil;
import com.pedidosapp.api.utils.StringUtil;
import com.pedidosapp.api.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CustomerValidator extends AbstractValidator {
    private final CustomerRepository customerRepository;

    public CustomerValidator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;

        try {
            List<RequiredField> requiredFields = new ArrayList<>();
            List<CharacterLengthField> characterLengthFields = new ArrayList<>();
            requiredFields.add(new RequiredField(Customer.class.getDeclaredField("name"), "nome"));
            characterLengthFields.add(new CharacterLengthField(Customer.class.getDeclaredField("name"), 3, false, "nome"));
            characterLengthFields.add(new CharacterLengthField(Customer.class.getDeclaredField("name"), 255, true, "nome"));

            super.addListOfRequiredFields(requiredFields);
            super.addListOfCharacterLengthFields(characterLengthFields);
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }

    @Override
    public void validate(Object object) {
        Customer customer = (Customer) object;
        String cpf = customer.getCpf();
        String cnpj = customer.getCnpj();

        super.validate(object);

        if (StringUtil.isNotNullOrEmpty(cpf)) {
            CpfUtil.validate(cpf);

            if (customerRepository.existsByCpfAndIdIsNot(cpf, Utils.nvl(customer.getId(), 0)))
                throw new ApplicationGenericsException(EnumUnauthorizedException.CPF_ALREADY_REGISTERED);
        }

        if (StringUtil.isNotNullOrEmpty(cnpj)) {
            CnpjUtil.validate(cnpj);

            if (customerRepository.existsByCnpjAndIdIsNot(cnpj, Utils.nvl(customer.getId(), 0)))
                throw new ApplicationGenericsException(EnumUnauthorizedException.CNPJ_ALREADY_REGISTERED);
        }
    }
}