package com.example.bookstore.controller;

import com.example.bookstore.model.Author;
import com.example.bookstore.dto.AuthorDTO;  // Import the AuthorDTO class
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;  // Injecting the AuthorService

    @Autowired
    private BookRepository bookRepository;

    // Create an author
    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody Author author) {
        Author createdAuthor = authorService.createAuthor(author);  // Use service to create the author
        AuthorDTO authorDTO = new AuthorDTO(createdAuthor);  // Convert to AuthorDTO
        return new ResponseEntity<>(authorDTO, HttpStatus.CREATED);  // Return AuthorDTO with status 201
    }

    // Retrieve author by ID
    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long authorId) {
        Optional<Author> author = authorService.getAuthorById(authorId);  // Use service to fetch author
        return author.map(value -> new ResponseEntity<>(new AuthorDTO(value), HttpStatus.OK))  // Convert to AuthorDTO
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));  // Return 404 if not found
    }

    // Retrieve books by author ID
    @GetMapping("/{authorId}/books")
    public ResponseEntity<List<Book>> getBooksByAuthorId(@PathVariable Long authorId) {
        List<Book> books = bookRepository.findBooksByAuthorId(authorId);
        if (!books.isEmpty()) {
            return new ResponseEntity<>(books, HttpStatus.OK);  // Return books with 200 status
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if no books are found
        }
    }
}
