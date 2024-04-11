package com.pedidosapp.api.repository;

import com.pedidosapp.api.model.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends
        JpaRepository<Order, Integer>,
        PagingAndSortingRepository<Order, Integer>,
        JpaSpecificationExecutor<Order>
{
}