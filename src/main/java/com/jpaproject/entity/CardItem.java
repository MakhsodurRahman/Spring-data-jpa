package com.jpaproject.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class CardItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iid;
    private String name;

    public CardItem(String name) {
        this.name = name;
    }


}
