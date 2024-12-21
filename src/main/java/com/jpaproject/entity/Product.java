package com.jpaproject.entity;

import jakarta.persistence.*;
import lombok.Data;
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

    // Getters and setters
}
