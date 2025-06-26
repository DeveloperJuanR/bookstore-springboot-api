package com.example.bookstore.repository;

import com.example.bookstore.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface CreditCardRepository extends JpaRepository <CreditCard, String> {
    @Query("SELECT c FROM CreditCard c WHERE c.userProfile.username = :username")
    List<CreditCard> findByUsername(@Param("username") String username);
}
