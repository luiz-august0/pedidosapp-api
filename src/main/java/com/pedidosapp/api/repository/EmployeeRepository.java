package com.pedidosapp.api.repository;

import com.pedidosapp.api.model.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository extends
        JpaRepository<Employee, Integer>,
        PagingAndSortingRepository<Employee, Integer>,
        JpaSpecificationExecutor<Employee>
{

}