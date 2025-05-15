package com.allobank.allobackendtest.service;

import com.allobank.allobackendtest.dto.*;
import com.allobank.allobackendtest.exception.ResourceNotFoundException;
import com.allobank.allobackendtest.model.Caleg;
import com.allobank.allobackendtest.model.Dapil;
import com.allobank.allobackendtest.model.JenisKelamin;
import com.allobank.allobackendtest.model.Partai;
import com.allobank.allobackendtest.repository.CalegRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalegServiceTest {

    @Mock
    private CalegRepository calegRepository;


    @InjectMocks
    private CalegService calegService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCaleg_ReturnsList() {
        UUID dapilId = UUID.randomUUID();
        UUID partaiId = UUID.randomUUID();
        Sort sort = Sort.by(Sort.Direction.ASC, "nomorUrut");
        int page = 0;
        Pageable pageable = PageRequest.of(page, 25, sort);

        Caleg caleg = buildCaleg(dapilId, partaiId);
        Page<Caleg> calegPage = new PageImpl<>(List.of(caleg), pageable, 1);

        when(calegRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(calegPage);

        PaginatedResponseDTO<CalegDTO> result = calegService.getAllCaleg(dapilId, partaiId, sort, page);

        assertEquals(1, result.getTotalItems());
        assertEquals(1, result.getTotalPages());
        assertEquals(1, result.getData().size());
        verify(calegRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void testGetAllCaleg_WithOnlyDapil() {
        UUID dapilId = UUID.randomUUID();
        Sort sort = Sort.by(Sort.Direction.DESC, "nama");
        int page = 1;
        Pageable pageable = PageRequest.of(page, 25, sort);

        Caleg caleg = buildCaleg(dapilId, UUID.randomUUID());
        Page<Caleg> pageResult = new PageImpl<>(List.of(caleg), pageable, 1);

        when(calegRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(pageResult);

        PaginatedResponseDTO<CalegDTO> result = calegService.getAllCaleg(dapilId, null, sort, page);

        assertNotNull(result);
        assertEquals(1, result.getData().size());
    }

    @Test
    void testGetAllCaleg_WithOnlyPartai() {
        UUID partaiId = UUID.randomUUID();
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        int page = 0;
        Pageable pageable = PageRequest.of(page, 25, sort);

        Caleg caleg = buildCaleg(UUID.randomUUID(), partaiId);
        Page<Caleg> pageResult = new PageImpl<>(List.of(caleg), pageable, 1);

        when(calegRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(pageResult);

        PaginatedResponseDTO<CalegDTO> result = calegService.getAllCaleg(null, partaiId, sort, page);

        assertEquals(1, result.getData().size());
        assertEquals(1, result.getTotalItems());
    }

    @Test
    void testGetAllCaleg_NullSort_UsesDefault() {
        UUID dapilId = UUID.randomUUID();
        UUID partaiId = UUID.randomUUID();
        int page = 0;

        Pageable pageable = PageRequest.of(page, 25, Sort.by(Sort.Direction.ASC, "nomorUrut"));
        Caleg caleg = buildCaleg(dapilId, partaiId);
        Page<Caleg> pageResult = new PageImpl<>(List.of(caleg), pageable, 1);

        when(calegRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(pageResult);

        PaginatedResponseDTO<CalegDTO> result = calegService.getAllCaleg(dapilId, partaiId, null, page);

        assertNotNull(result);
        assertEquals(1, result.getTotalItems());
    }

    @Test
    void testGetCalegById_ReturnsDetailCalegDTO() {
        UUID id = UUID.randomUUID();
        Caleg caleg = buildCaleg(UUID.randomUUID(), UUID.randomUUID());

        when(calegRepository.findById(id)).thenReturn(Optional.of(caleg));

        DetailCalegDTO result = calegService.getCalegById(id);
        DetailCalegDTO expected = buildDetailCalegDTO(caleg);

        assertNotNull(result);
        assertEquals(caleg.getId(), result.getId());
        assertEquals(caleg.getDapil().getNamaDapil(), result.getDapil().getNama());
        assertEquals(expected, result);
    }

    @Test
    void testGetCalegById_NotFound_ThrowsException() {
        UUID id = UUID.randomUUID();
        when(calegRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> calegService.getCalegById(id));
    }

    private Caleg buildCaleg(UUID dapilId, UUID partaiId) {
        Dapil dapil = new Dapil();
        dapil.setId(dapilId);
        dapil.setNamaDapil("Jakarta I");
        dapil.setProvinsi("DKI Jakarta");
        dapil.setJumlahKursi(10);
        dapil.setWilayahDapilList(List.of("Jakarta Selatan", "Jakarta Pusat"));

        Partai partai = new Partai();
        partai.setId(partaiId);
        partai.setNamaPartai("Partai A");
        partai.setNomorUrut(1);

        Caleg caleg = new Caleg();
        caleg.setId(UUID.randomUUID());
        caleg.setNama("John Doe");
        caleg.setNomorUrut(1);
        caleg.setJenisKelamin(JenisKelamin.LAKILAKI);
        caleg.setDapil(dapil);
        caleg.setPartai(partai);

        return caleg;
    }

    private DetailCalegDTO buildDetailCalegDTO(Caleg caleg) {
        return DetailCalegDTO.builder()
                .id(caleg.getId())
                .nomorUrut(caleg.getNomorUrut())
                .nama(caleg.getNama())
                .jenisKelamin(caleg.getJenisKelamin())
                .dapil(
                        DetailDapilDTO.builder()
                                .id(caleg.getDapil().getId())
                                .nama(caleg.getDapil().getNamaDapil())
                                .provinsi(caleg.getDapil().getProvinsi())
                                .wilayah(caleg.getDapil().getWilayahDapilList())
                                .jumlahKursi(caleg.getDapil().getJumlahKursi())
                                .build()
                )
                .partai(
                        PartaiDTO.builder()
                                .id(caleg.getPartai().getId())
                                .nama(caleg.getPartai().getNamaPartai())
                                .nomorUrut(caleg.getPartai().getNomorUrut())
                                .build()
                )
                .build();
    }

}
