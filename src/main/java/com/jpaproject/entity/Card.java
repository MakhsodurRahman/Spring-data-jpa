package com.jpaproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cid;

    private String userName;

    @OneToMany(cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<CardItem> cardItems;

    public Card(String userName, List<CardItem> cardItems) {
        this.userName = userName;
        this.cardItems = cardItems;
    }
}
