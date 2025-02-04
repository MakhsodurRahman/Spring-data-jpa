package com.jpaproject.repository;

import com.jpaproject.entity.AppUser;
import com.jpaproject.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {

    Orders findByIdAndUser(Long id, AppUser appUser);

//    @Async
//    CompletableFuture<Orders> findByIdAndUser(Long id,AppUser appUser);
}
