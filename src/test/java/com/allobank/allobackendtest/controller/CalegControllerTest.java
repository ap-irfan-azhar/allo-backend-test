package com.allobank.allobackendtest.controller;

import com.allobank.allobackendtest.dto.CalegDTO;
import com.allobank.allobackendtest.dto.DetailCalegDTO;
import com.allobank.allobackendtest.dto.PaginatedResponseDTO;
import com.allobank.allobackendtest.service.CalegService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class CalegControllerTest {

    @Mock
    private CalegService calegService;

    @InjectMocks
    private CalegController calegController;

    private UUID dummyId;

    @BeforeEach
    void setUp() {
        openMocks(this);
        dummyId = UUID.randomUUID();
    }

    @Test
    void testGetAllCaleg_success() {
        PaginatedResponseDTO<CalegDTO> responseDTO = new PaginatedResponseDTO<>();
        responseDTO.setData(Collections.emptyList());
        responseDTO.setTotalItems(0);
        responseDTO.setTotalPages(0);
        responseDTO.setTotalItems(0);

        when(calegService.getAllCaleg(null, null, Sort.by(Sort.Direction.ASC, "nomorUrut"), 0))
                .thenReturn(responseDTO);

        ResponseEntity<PaginatedResponseDTO<CalegDTO>> response = calegController.getAllCaleg(
                null, null, Sort.Direction.ASC, 0);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(responseDTO, response.getBody());
        verify(calegService).getAllCaleg(null, null, Sort.by(Sort.Direction.ASC, "nomorUrut"), 0);
    }

    @Test
    void testGetAllCaleg_failure() {
        when(calegService.getAllCaleg(null, null, Sort.by(Sort.Direction.ASC, "nomorUrut"), 0))
                .thenThrow(new RuntimeException("Service error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            calegController.getAllCaleg(null, null, Sort.Direction.ASC, 0);
        });

        assertEquals("Service error", exception.getMessage());
        verify(calegService).getAllCaleg(null, null, Sort.by(Sort.Direction.ASC, "nomorUrut"), 0);
    }

    @Test
    void testGetCalegById_success() {
        DetailCalegDTO detailCalegDTO = new DetailCalegDTO();
        detailCalegDTO.setId(dummyId);

        when(calegService.getCalegById(dummyId)).thenReturn(detailCalegDTO);

        ResponseEntity<DetailCalegDTO> response = calegController.getCalegById(dummyId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(detailCalegDTO, response.getBody());
        verify(calegService).getCalegById(dummyId);
    }

    @Test
    void testGetCalegById_notFound() {
        when(calegService.getCalegById(dummyId))
                .thenThrow(new IllegalArgumentException("Caleg not found"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calegController.getCalegById(dummyId);
        });

        assertEquals("Caleg not found", exception.getMessage());
        verify(calegService).getCalegById(dummyId);
    }

    @Test
    void testGetCalegById_genericError() {
        when(calegService.getCalegById(dummyId))
                .thenThrow(new RuntimeException("Unexpected error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            calegController.getCalegById(dummyId);
        });

        assertEquals("Unexpected error", exception.getMessage());
        verify(calegService).getCalegById(dummyId);
    }
}
