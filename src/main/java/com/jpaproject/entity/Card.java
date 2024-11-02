package com.jpaproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Entity
@Setter
@ToString
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cid;

    private String userName;

    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REMOVE,CascadeType.REFRESH,CascadeType.MERGE},orphanRemoval = true)
    private List<CardItem> cardItems;

    public Card(String userName, List<CardItem> cardItems) {
        this.userName = userName;
        this.cardItems = cardItems;
    }
}
