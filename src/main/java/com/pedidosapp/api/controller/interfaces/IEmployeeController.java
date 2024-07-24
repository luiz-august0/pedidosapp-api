package com.pedidosapp.api.controller.interfaces;

import com.pedidosapp.api.model.beans.EmployeeBean;
import com.pedidosapp.api.model.dtos.EmployeeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.pedidosapp.api.constants.Paths.prefixPath;

@RequestMapping(IEmployeeController.PATH)
public interface IEmployeeController extends IAbstractAllGetController<EmployeeDTO> {
    String PATH = prefixPath + "/employee";

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    EmployeeDTO insert(@RequestBody EmployeeBean bean);

    @PutMapping("/{id}")
    EmployeeDTO update(@PathVariable("id") Integer id, @RequestBody EmployeeBean bean);

    @PutMapping("/{id}/activate")
    EmployeeDTO activateInactivate(@PathVariable("id") Integer id, @RequestParam(value = "active", defaultValue = "true") Boolean active);

}