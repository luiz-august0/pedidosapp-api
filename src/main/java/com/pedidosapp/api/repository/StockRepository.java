package com.pedidosapp.api.repository;

import com.pedidosapp.api.model.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StockRepository extends
        JpaRepository<Stock, Integer>,
        PagingAndSortingRepository<Stock, Integer>,
        JpaSpecificationExecutor<Stock>
{

}