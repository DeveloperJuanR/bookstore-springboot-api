package com.example.bookstore.service;

import com.example.bookstore.dto.BookRatingDTO;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.RatingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingsService {

    private final RatingsRepository ratingsRepository;

    public RatingsService(RatingsRepository ratingsRepository) {
        this.ratingsRepository = ratingsRepository;
    }

    public List<BookRatingDTO> getBooksByRating(int rating) {

        if (rating > 5 || rating < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rating must be a value between 0 and 5");
        }

        System.out.println("Searching for books with genre greater than or equal to: " + rating);

        List<BookRatingDTO> books = ratingsRepository.findDistinctTopByRatingGreaterThanEqual(rating);

        if (books.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No books found with a rating greater than or equal to: " + rating);
        }
        return books.stream()
                .map(book -> new BookRatingDTO(book.getIsbn(), book.getTitle(), book.getAverageRating()))
                .collect(Collectors.toList());
    }

}
