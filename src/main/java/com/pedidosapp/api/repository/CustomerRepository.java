package com.pedidosapp.api.repository;

import com.pedidosapp.api.model.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends
        JpaRepository<Customer, Integer>,
        PagingAndSortingRepository<Customer, Integer>,
        JpaSpecificationExecutor<Customer> {
    Boolean existsByCpfAndIdIsNot(String cpf, Integer id);

    Boolean existsByCnpjAndIdIsNot(String cnpj, Integer id);
}