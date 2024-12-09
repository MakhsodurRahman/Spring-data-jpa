package com.jpaproject.entity;

import jakarta.persistence.*;
import lombok.ToString;

@Entity
@ToString
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
