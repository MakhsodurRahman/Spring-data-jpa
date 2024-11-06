package com.jpaproject.repository;

import com.jpaproject.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class SqlServerStudentRepository {

    @PersistenceContext(unitName = "sql-server-unit")
    private EntityManager entityManager;

    public List<Student> findAll(){
        return entityManager.createQuery("select s from Student s",Student.class)
                .getResultList();
    }

    public Optional<Student> findById(Integer id){
        return Optional.ofNullable(this.entityManager.find(Student.class,id));
    }

    @Transactional
    public void save(Student student){
        entityManager.persist(student);
    }
}
