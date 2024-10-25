package com.jpaproject.entity;


import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course")
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;

    public Course(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
