package com.pedidosapp.api.service.validators;

import com.pedidosapp.api.model.entities.Supplier;
import com.pedidosapp.api.repository.SupplierRepository;
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

public class SupplierValidator extends AbstractValidator {

    private final SupplierRepository supplierRepository;

    public SupplierValidator(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;

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

    @Override
    public void validate(Object object) {
        Supplier supplier = (Supplier) object;
        String cpf = supplier.getCpf();
        String cnpj = supplier.getCnpj();

        super.validate(object);

        if (StringUtil.isNotNullOrEmpty(cpf)) {
            CpfUtil.validate(cpf);

            if (supplierRepository.existsByCpfAndIdIsNot(cpf, Utils.nvl(supplier.getId(), 0)))
                throw new ApplicationGenericsException(EnumUnauthorizedException.CPF_ALREADY_REGISTERED);
        }

        if (Utils.isNotEmpty(cnpj)) {
            CnpjUtil.validate(cnpj);

            if (supplierRepository.existsByCnpjAndIdIsNot(cnpj, Utils.nvl(supplier.getId(), 0)))
                throw new ApplicationGenericsException(EnumUnauthorizedException.CNPJ_ALREADY_REGISTERED);
        }
    }
}