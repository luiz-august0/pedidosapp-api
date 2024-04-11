package com.pedidosapp.api.repository;

import com.pedidosapp.api.model.entities.ProductSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductSupplierRepository extends
        JpaRepository<ProductSupplier, Integer>,
        PagingAndSortingRepository<ProductSupplier, Integer>,
        JpaSpecificationExecutor<ProductSupplier>
{

}