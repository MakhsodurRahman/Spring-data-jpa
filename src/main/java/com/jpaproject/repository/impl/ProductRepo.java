package com.jpaproject.repository.impl;

import com.jpaproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Stream;


@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    //Streamable<Product> findAllByNameLike(String name);
    //Streamable<Product> findAllByCategory_name(String cegetoryName);


    Stream<Product> findAllByCategory_name(String name);
    List<Product> findAllByCategoryName(String name);
}
