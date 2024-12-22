package com.jpaproject.repository.impl;

import com.jpaproject.dto.ProductDto;
import com.jpaproject.dto.ProductNamePriceDto;
import com.jpaproject.entity.Product;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    //Streamable<Product> findAllByNameLike(String name);
    //Streamable<Product> findAllByCategory_name(String cegetoryName);


    //Stream<Product> findAllByCategory_name(String name);
    //Page<Product> findAllByCategory_nameOrderByCategoryName(String name, Pageable pageable);
    //Page<Product> findAllByCategory_name(String name, Pageable pageable);
//    List<Product> findAllByCategory_name(String name, Limit limit, Sort sort);
//    List<Product> findAllByCategoryName(String name);

    Window<Product> findProductsByCategory_NameInOrderById(List<String> categories,ScrollPosition position,Limit limit,Sort sort);

//    @Query("SELECT p.name, p.price FROM Product p WHERE p.id = :category")
//    List<Object[]> findProductNameAndPriceById(@Param("category") int id);

//    List<Object[]> findProductsByCategory_Name(String categoryName);
    /*

    @Query("select p.id,p.name,p.price  from Product p where p.id = :id")
    Object[] findProductById(int id);


     when use this code return TupleBackMap this is a proxy.
    @Query("select p.id as product_id,p.name as name,p.price as price  from Product p where p.id = ?1")
    Map<String ,?> findProductById(Integer id);

    //if not use TupleBackMap use below code. when use this code return HashMap
    @Query("select new Map( p.id as product_id,p.name as name,p.price as price ,p.category.name as categoryName) from Product p where p.id = ?1")
    Map<String ,?> findProductById(Integer id);



    @Query("select  p.id as product_id,p.name as name,p.price as price ,p.category.name as categoryName from Product p where p.id = ?1")
    Tuple findProductById(Integer id);


// dto base projection example 1
    //ProductNamePriceDto findProductById(Integer id);

    // dto base projection example 2
    @Query("select new com.jpaproject.dto.ProductNamePriceDto(p.name ,p.price,p.category.name) from Product p where p.id = ?1")
    ProductNamePriceDto findProductById(Integer id);

    // dto base projection example 3

    @Query("select new com.jpaproject.dto.ProductNamePriceDto(p.name ,p.price,p.category.name) from Product p where p.category.name = ?1")
    Page<ProductNamePriceDto> findProductsByCategoryName(String name,Pageable pageable);


    //@Query("select new Product(p.id,p.categoryName,p.name) from Product p where p.id = ?1")
   // when use this type of projection and if change any value of service method and this method use transactional annotation change will be update
    Product findProductById(Integer id);

     */

    @Query("select new com.jpaproject.dto.ProductDto(p.id,p.name,p.price,p.categoryName) from Product p where p.id = ?1")
    ProductDto findProductAllInfoById(Integer id);


    @Query("select new com.jpaproject.dto.ProductNamePriceDto(p.name,p.price) from Product p where p.id = ?1")
    ProductNamePriceDto findProductNameAndPriceById(Integer id);




}

// object base project problem is find value using index
// map base project problem is get value then cast every column
//