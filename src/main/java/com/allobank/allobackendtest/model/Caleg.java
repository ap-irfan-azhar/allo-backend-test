package com.allobank.allobackendtest.model;

import lombok.Data;
import jakarta.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "caleg")
public class Caleg {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "dapil_id")
    private Dapil dapil;

    @ManyToOne
    @JoinColumn(name = "partai_id")
    private Partai partai;

    private Integer nomorUrut;

    private String nama;

    @Enumerated(EnumType.STRING)
    private JenisKelamin jenisKelamin;
}
