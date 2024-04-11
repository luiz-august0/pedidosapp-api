package com.pedidosapp.api.repository;

import com.pedidosapp.api.model.entities.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PurchaseOrderRepository extends
        JpaRepository<PurchaseOrder, Integer>,
        PagingAndSortingRepository<PurchaseOrder, Integer>,
        JpaSpecificationExecutor<PurchaseOrder>
{
}