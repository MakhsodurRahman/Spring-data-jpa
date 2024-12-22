package com.jpaproject.dto;

public record ProductDto(
        Long id,
        String name,
        Long price,
        String category
) {
}
