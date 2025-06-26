package com.example.bookstore.controller;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.dto.BookRatingDTO;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Publisher;
import com.example.bookstore.service.BookService;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.RatingsService;
import com.example.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;
import com.example.bookstore.model.Author;
import com.example.bookstore.repository.AuthorRepository;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.persistence.Column;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private RatingsService ratingsService;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;


    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/genre/{genre}")
    public List<BookDTO> getBooksByGenre(@PathVariable String genre) {
        return bookService.getBooksByGenre(genre);
    }

    @GetMapping("/rating/{rating}")
    public List<BookRatingDTO> getBooksByRating(@PathVariable Integer rating) {
        return ratingsService.getBooksByRating(rating);
    }

    @GetMapping("/publisher/{publisherId}")
    public List<Book> getBooksByPublisherId(@PathVariable Long publisherId) {
        return bookService.getBooksByPublisherId(publisherId);
    }

    @PatchMapping("/discount")
    public void discountBooksByPublisher(@RequestParam(value = "percentage", required = true) Double percentage,
                                                     @RequestParam(value = "publisherId", required = true) Long publisherId) {

        bookService.discountBooksByPublisher(percentage, publisherId);
    }

    @GetMapping("/top-sellers")
    public List<BookDTO> getTopSellers() {
        return bookService.getTopSellers();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        System.out.println("Received ISBN: " + isbn);  // Add logging here too
        Book book = bookService.getBookByISBN(isbn);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Book>> getBooksByAuthorId(@PathVariable Long authorId) {
        List<Book> books = bookRepository.findBooksByAuthorId(authorId);
        if (!books.isEmpty()) {
            return new ResponseEntity<>(books, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Void> addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
