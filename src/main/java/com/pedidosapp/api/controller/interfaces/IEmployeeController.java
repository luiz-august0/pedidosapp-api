package com.pedidosapp.api.controller.interfaces;

import com.pedidosapp.api.model.beans.EmployeeBean;
import com.pedidosapp.api.model.dtos.EmployeeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.pedidosapp.api.constants.Paths.prefixPath;

@RequestMapping(IEmployeeController.PATH)
public interface IEmployeeController extends IAbstractAllGetController<EmployeeDTO> {
    String PATH = prefixPath + "/employee";

    @PostMapping
    ResponseEntity<EmployeeDTO> insert(@RequestBody EmployeeBean bean);

    @PutMapping("/{id}")
    ResponseEntity<EmployeeDTO> update(@PathVariable("id") Integer id, @RequestBody EmployeeBean bean);

    @PutMapping("/{id}/activate")
    ResponseEntity<EmployeeDTO> activateInactivate(@PathVariable("id") Integer id, @RequestParam(value = "active", defaultValue = "true") Boolean active);
}