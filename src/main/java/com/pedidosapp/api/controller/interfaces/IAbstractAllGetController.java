package com.pedidosapp.api.controller.interfaces;

import com.pedidosapp.api.model.dtos.AbstractDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface IAbstractAllGetController<DTO extends AbstractDTO> {
    @GetMapping
    List<DTO> findAll();

    @GetMapping("/filter")
    List<DTO> findAllFiltered(@RequestParam(required = false) Map<String, Object> filters);

    @GetMapping("/filter/page")
    Page<DTO> findAllFilteredAndPageable(@RequestParam(required = false) Map<String, Object> filters, Pageable pageable);

    @GetMapping("/{id}")
    DTO findById(@PathVariable("id") Integer id);
}
