package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    @Query("SELECT b FROM Book b WHERE b.publisher.publisherId = :publisherId")
    List<Book> getBooksByPublisherId(@Param("publisherId") Long publisherID);
}