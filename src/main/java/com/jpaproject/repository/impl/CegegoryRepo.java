package com.jpaproject.repository.impl;

import com.jpaproject.entity.Category;
import com.jpaproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

@Repository
public interface CegegoryRepo extends JpaRepository<Category,Long> {


}
