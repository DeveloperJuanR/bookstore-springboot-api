package com.example.bookstore.repository;

import com.example.bookstore.dto.BookRatingDTO;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Ratings;
import com.example.bookstore.model.RatingsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingsRepository extends JpaRepository<Ratings, RatingsId> {

    @Query("SELECT new com.example.bookstore.dto.BookRatingDTO(b.ISBN, b.title, ROUND(AVG(r.rating), 1)) " +
            "FROM Ratings r JOIN r.book b " +
            "GROUP BY b.ISBN, b.title " +
            "HAVING AVG(r.rating) >= :minRating " +
            "ORDER BY AVG(r.rating) DESC")
    List<BookRatingDTO> findDistinctTopByRatingGreaterThanEqual(@Param("minRating") double minRating);
}


