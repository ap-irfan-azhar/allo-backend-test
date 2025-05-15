package com.allobank.allobackendtest.service;

import com.allobank.allobackendtest.dto.CalegDTO;
import com.allobank.allobackendtest.dto.DapilDTO;
import com.allobank.allobackendtest.dto.PartaiDTO;
import com.allobank.allobackendtest.dto.DetailCalegDTO;
import com.allobank.allobackendtest.dto.DetailDapilDTO;
import com.allobank.allobackendtest.dto.PaginatedResponseDTO;
import com.allobank.allobackendtest.exception.ResourceNotFoundException;
import com.allobank.allobackendtest.model.Caleg;
import com.allobank.allobackendtest.repository.CalegRepository;
import com.allobank.allobackendtest.specification.CalegSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;



import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalegService implements ICalegService {

    private final CalegRepository calegRepository;

    @Override
    @Cacheable(
            value = "calegList",
            key = "#root.methodName + '_' + #dapilId + '_' + #partaiId + '_' + #sort.toString() + '_' + #page",
            unless = "#result == null"
    )
    public PaginatedResponseDTO<CalegDTO> getAllCaleg(UUID dapilId, UUID partaiId, Sort sort, int page) {
        Specification<Caleg> spec = Specification.where(null);

        if (dapilId != null) {
            spec = spec.and(CalegSpecification.hasDapilId(dapilId));
        }

        if (partaiId != null) {
            spec = spec.and(CalegSpecification.hasPartaiId(partaiId));
        }

        if (sort == null) {
            sort = Sort.by(Sort.Direction.ASC, "nomorUrut");
        }

        Pageable pageable = PageRequest.of(page, 25, sort);
        var calegPage = calegRepository.findAll(spec, pageable);

        List<CalegDTO> content = calegPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return PaginatedResponseDTO.<CalegDTO>builder()
                .data(content)
                .totalItems(calegPage.getTotalElements())
                .totalPages(calegPage.getTotalPages())
                .build();
    }



    @Cacheable(value = "caleg", key = "#root.methodName + '_' + #id.toString()", unless = "#result == null")
    public DetailCalegDTO getCalegById(UUID id) {
        return calegRepository.findById(id)
                .map(this::convertToDetailDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Caleg not found with id: " + id));
    }

    private CalegDTO convertToDTO(Caleg caleg) {
        return CalegDTO.builder()
                .id(caleg.getId())
                .nomorUrut(caleg.getNomorUrut())
                .nama(caleg.getNama())
                .jenisKelamin(caleg.getJenisKelamin())
                .dapil(
                        DapilDTO.builder()
                                .id(caleg.getDapil().getId())
                                .nama(caleg.getDapil().getNamaDapil())
                                .provinsi(caleg.getDapil().getProvinsi())
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
    private DetailCalegDTO convertToDetailDTO(Caleg caleg) {
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