package com.jpaproject.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

public record ProductSearchDto(
        String search,
        String category,
        double minPrice,
        double maxPrice,
        List<String> brands
) {
}
