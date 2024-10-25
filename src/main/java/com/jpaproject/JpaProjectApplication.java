package com.jpaproject;

import com.jpaproject.entity.Course;
import com.jpaproject.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.function.Consumer;


@SpringBootApplication
public class JpaProjectApplication {

	public static final EntityManagerFactory fectory = Persistence.createEntityManagerFactory("sql-server-unit");
	public static final EntityManager em = fectory.createEntityManager();

	static void insert(EntityManager em){
		Course course = new Course("101","Alog");
		//if not save course can't save student in db if not use casecade
		Student student = new Student("mak",3.4,course);
		em.persist(student);
	}

	static void remove(EntityManager em,Object object){
		em.remove(object);
	}

	public static void main(String[] args) {
		SpringApplication.run(JpaProjectApplication.class, args);

		transactional(em->{

			//insert(em);

			var s = em.find(Student.class,5);
			System.out.println(s.getCourse());
			s.setCourse(null);
			em.remove(s);
		});

	}

	public static void transactional(Consumer<EntityManager> consumer){
		em.getTransaction().begin();// before advice
		try{
			consumer.accept(em);
		}catch (Exception e){
			em.getTransaction().rollback();// after throwing
			throw new RuntimeException("Can't save the object");
		}
		em.getTransaction().commit();// after advice

	}

}



