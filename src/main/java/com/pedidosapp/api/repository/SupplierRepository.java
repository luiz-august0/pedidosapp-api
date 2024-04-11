package com.pedidosapp.api.repository;

import com.pedidosapp.api.model.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SupplierRepository extends
        JpaRepository<Supplier, Integer>,
        PagingAndSortingRepository<Supplier, Integer>,
        JpaSpecificationExecutor<Supplier>
{

}