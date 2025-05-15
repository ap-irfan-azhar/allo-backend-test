package com.allobank.allobackendtest.controller;

import com.allobank.allobackendtest.dto.CalegDTO;
import com.allobank.allobackendtest.dto.DetailCalegDTO;
import com.allobank.allobackendtest.dto.PaginatedResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface ICalegController {
    ResponseEntity<PaginatedResponseDTO<CalegDTO>> getAllCaleg(
            @RequestParam(required = false) UUID dapilId,
            @RequestParam(required = false) UUID partaiId,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortDirection,
            @RequestParam(defaultValue = "0") int page
    );

    ResponseEntity<DetailCalegDTO> getCalegById(@PathVariable UUID id);
}
