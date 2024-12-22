package com.jpaproject.dto;

public record ProductIdNamePriceDto(
        int id,
        String name,
        Long price
) {
}
