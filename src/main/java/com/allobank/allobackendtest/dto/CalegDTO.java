package com.allobank.allobackendtest.dto;

import com.allobank.allobackendtest.model.JenisKelamin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalegDTO implements Serializable {
    private UUID id;
    private Integer nomorUrut;
    private String nama;
    private JenisKelamin jenisKelamin;

    private DapilDTO dapil;

    private PartaiDTO partai;
}