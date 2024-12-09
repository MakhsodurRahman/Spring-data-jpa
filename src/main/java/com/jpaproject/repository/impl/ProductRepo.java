package com.jpaproject.repository.impl;

import com.jpaproject.entity.Product;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    //Stream<Product> findAllByCategory_name(String name);
    //Page<Product> findAllByCategory_nameOrderByCategoryName(String name, Pageable pageable);
    //Page<Product> findAllByCategory_name(String name, Pageable pageable);
    List<Product> findAllByCategory_name(String name, Limit limit, Sort sort);
    List<Product> findAllByCategoryName(String name);
}
