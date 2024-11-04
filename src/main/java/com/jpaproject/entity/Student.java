package com.jpaproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.sql.ast.tree.expression.Star;

import java.lang.annotation.Target;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;

    private String name;
    private double cgpa;

   //@OneToOne(cascade = {CascadeType.PERSIST})
   /*
   @OneToOne(cascade = {CascadeType.PERSIST})
   when use this when save student object this time save course object,
    if not found course object in db
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    if not use foreign key constraint use this code

    @OneToOne(optional = false)
    when use this course must be not null when insert student


    @OneToOne(optional = false,cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    when use CasecadeType.REMOVE -> when delete student in this time delete course when OneToOne relation
    but if i delete student using sql query not delete course.
    */
//   @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval = true)
//    private Course course;

    public Student(String name, double cgpa) {
        this.name = name;
        this.cgpa = cgpa;

    }
}
