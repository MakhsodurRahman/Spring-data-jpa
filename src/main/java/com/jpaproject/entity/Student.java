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
@AllArgsConstructor
@Entity
//@ToString(exclude = "courses")
//@DynamicUpdate
//@NamedEntityGraphs({
//        @NamedEntityGraph(name = "studentWithCourse",attributeNodes = @NamedAttributeNode("courses"))
//}) if use dynamic graph on runtime not use that
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;

    private String name;
    private double cgpa;

//    @OneToOne(targetEntity = Address.class)
//    @JoinColumn(name = "address_identifiers",referencedColumnName = "aid",foreignKey = @ForeignKey(name = "FK_student_id",value = ConstraintMode.NO_CONSTRAINT))
//    private Address address;

   @OneToOne
    private Course course;
}
