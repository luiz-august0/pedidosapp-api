package com.pedidosapp.api.repository;

import com.pedidosapp.api.model.entities.PurchaseOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PurchaseOrderItemRepository extends
        JpaRepository<PurchaseOrderItem, Integer>,
        PagingAndSortingRepository<PurchaseOrderItem, Integer>,
        JpaSpecificationExecutor<PurchaseOrderItem>
{
}