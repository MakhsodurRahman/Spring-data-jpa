package com.jpaproject.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
@Getter
@Setter
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aid;
    private String house;
    private Integer road;
    @OneToOne(mappedBy = "address")
    /*
     this is called ownership transfer to student table column.this is called ownership delegation
     use mappedBy using delegate ownership to other entity
      when use it address table not contain any foreign key in address table for student table.
      if not use mappedBy = "address" create new column for student table to get student info using address table
     */
    private Student student;// this is Bidirectional mapping

    public Address(String house, Integer road) {
        this.house = house;
        this.road = road;
    }

}
