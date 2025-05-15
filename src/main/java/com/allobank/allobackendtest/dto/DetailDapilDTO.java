package com.allobank.allobackendtest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailDapilDTO implements Serializable {
    private UUID id;
    private String nama;
    private String provinsi;
    private List<String> wilayah;
    private int jumlahKursi;
}
