package com.allobank.allobackendtest.model;

import lombok.Data;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "dapil")
public class Dapil {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String namaDapil;

    private String provinsi;

    @ElementCollection
    @CollectionTable(name = "dapil_wilayah", joinColumns = @JoinColumn(name = "dapil_id"))
    @Column(name = "wilayah")
    private List<String> wilayahDapilList;

    private int jumlahKursi;
}
