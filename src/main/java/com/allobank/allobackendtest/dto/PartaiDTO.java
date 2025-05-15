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
public class PartaiDTO implements Serializable{
    private UUID id;
    private String nama;
    private Integer nomorUrut;
}
