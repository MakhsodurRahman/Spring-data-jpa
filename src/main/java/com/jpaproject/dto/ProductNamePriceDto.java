package com.jpaproject.dto;

import lombok.ToString;

public record ProductNamePriceDto(
        String name,
        Long price
) {
}
