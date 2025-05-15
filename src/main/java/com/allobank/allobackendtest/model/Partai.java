package com.allobank.allobackendtest.model;

import lombok.Data;
import jakarta.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "partai")
public class Partai {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String namaPartai;

    private Integer nomorUrut;
}
