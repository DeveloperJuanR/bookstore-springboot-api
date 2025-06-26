package com.example.bookstore.repository;

import com.example.bookstore.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByUserUserId(Long userID);

}

