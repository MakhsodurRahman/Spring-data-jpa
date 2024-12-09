package com.jpaproject.repository.impl;

import com.jpaproject.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.parser.PartTree;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student,Long> {

    Optional<Student> findBySid(Long id);

}
