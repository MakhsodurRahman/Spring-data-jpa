package com.jpaproject.projection;

import com.jpaproject.entity.Category;

public interface ProductNamePriceCategoryProjection {
    String getProName();
    Long getPrice();
    CategoryNameProjection getCategory();

    interface CategoryNameProjection{
        String getName();
    }
}
