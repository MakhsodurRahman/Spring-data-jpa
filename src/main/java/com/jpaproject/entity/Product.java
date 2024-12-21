package com.jpaproject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Table(
        indexes = {
                @Index(name = "price_index",columnList = "price"),
                @Index(name = "id_and_price_index",columnList = "id"),
        }
)
@Entity
@ToString
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;


    private String name;
    private Long price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(Long id, String categoryName, String name) {
        this.id = id;
        this.categoryName = categoryName;
        this.name = name;
    }
}
