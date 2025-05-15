package com.allobank.allobackendtest.service;

import com.allobank.allobackendtest.dto.CalegDTO;
import com.allobank.allobackendtest.dto.DetailCalegDTO;
import com.allobank.allobackendtest.dto.PaginatedResponseDTO;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public interface ICalegService {
    PaginatedResponseDTO<CalegDTO> getAllCaleg(UUID dapilId, UUID partaiId, Sort sort, int page);
    DetailCalegDTO getCalegById(UUID id);
}
