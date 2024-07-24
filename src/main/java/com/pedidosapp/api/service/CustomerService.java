package com.pedidosapp.api.service;

import com.pedidosapp.api.model.entities.Customer;
import com.pedidosapp.api.repository.CustomerRepository;
import com.pedidosapp.api.validators.CustomerValidator;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends AbstractService<CustomerRepository, Customer, CustomerValidator> {
    CustomerService(CustomerRepository repository) {
        super(repository, new Customer(), new CustomerValidator(repository));
    }

}