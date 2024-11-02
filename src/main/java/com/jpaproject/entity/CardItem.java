package com.jpaproject.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@ToString(exclude = "card")
@NoArgsConstructor
public class CardItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iid;
    private String name;

    @ManyToOne
    private Card card;

    public CardItem(String name) {
        this.name = name;
    }


}
