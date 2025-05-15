package com.allobank.allobackendtest.controller;

import com.allobank.allobackendtest.dto.CalegDTO;
import com.allobank.allobackendtest.dto.DetailCalegDTO;
import com.allobank.allobackendtest.dto.PaginatedResponseDTO;
import com.allobank.allobackendtest.service.CalegService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/caleg")
@RequiredArgsConstructor
public class CalegController implements ICalegController{

    private final CalegService calegService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<CalegDTO>> getAllCaleg(
            @RequestParam(required = false) UUID dapilId,
            @RequestParam(required = false) UUID partaiId,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortDirection,
            @RequestParam(defaultValue = "0") int page
    ) {
        Sort sort = Sort.by(sortDirection, "nomorUrut");
        PaginatedResponseDTO<CalegDTO> calegList = calegService.getAllCaleg(dapilId, partaiId, sort, page);
        return ResponseEntity.ok(calegList);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DetailCalegDTO> getCalegById(@PathVariable UUID id) {
        DetailCalegDTO caleg = calegService.getCalegById(id);
        return ResponseEntity.ok(caleg);
    }
}