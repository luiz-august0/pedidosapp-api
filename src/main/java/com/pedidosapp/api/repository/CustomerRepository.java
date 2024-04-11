package com.pedidosapp.api.repository;

import com.pedidosapp.api.model.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CustomerRepository extends
        JpaRepository<Customer, Integer>,
        PagingAndSortingRepository<Customer, Integer>,
        JpaSpecificationExecutor<Customer>
{
    List<Customer> findAllByCpf(String cpf);

    List<Customer> findAllByCnpj(String cnpj);

    List<Customer> findAllByCpfAndIdIsNot(String cpf, Integer id);

    List<Customer> findAllByCnpjAndIdIsNot(String cnpj, Integer id);
}