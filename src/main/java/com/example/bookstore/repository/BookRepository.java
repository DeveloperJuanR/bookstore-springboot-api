package com.example.bookstore.repository;

import java.util.List;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, String> {

    @Query("SELECT b FROM Book b WHERE b.isbn = :isbn")
    Optional<Book> findByIsbn(@Param("isbn") String isbn);

    @Query("SELECT b FROM Book b JOIN BookAuthors ba ON b.isbn = ba.id.ISBN WHERE ba.id.authorId = :authorId")
    List<Book> findBooksByAuthorId(@Param("authorId") Long authorId);

    List<Book> findByGenreIgnoreCase(String genre);

    @Query("SELECT b FROM Book b ORDER BY b.copiesSold DESC")
    List<Book> findTopTenSellers();

    @Modifying
    @Query("UPDATE Book b SET b.price = b.price - (b.price * (:percentage /100.0)) WHERE b.publisher.publisherId = :publisherId")
    int discountBooksByPublisher(@Param("percentage") Double percentage, @Param("publisherId") Long publisherId);

    @Query("SELECT COUNT(b) from Book b WHERE b.publisher.publisherId = :publisherId")
    int countBooksByPublisherId(@Param("publisherId") Long publisherId);
}


