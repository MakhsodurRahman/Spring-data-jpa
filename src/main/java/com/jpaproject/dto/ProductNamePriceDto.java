package com.jpaproject.dto;

import lombok.ToString;

@ToString
public class ProductNamePriceDto {
    private final String name;
    private final Long price;
    private final String categoryName;

    public ProductNamePriceDto(String name, Long price, String cagetoryName) {
        this.name = name;
        this.price = price;
        this.categoryName = cagetoryName;
    }
}
