package com.allobank.allobackendtest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponseDTO<T> {
    private List<T> data;
    private long totalItems;
    private int totalPages;
}
