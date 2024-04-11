package com.pedidosapp.api.repository;

import com.pedidosapp.api.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends
        JpaRepository<Product, Integer>,
        PagingAndSortingRepository<Product, Integer>,
        JpaSpecificationExecutor<Product>
{

}