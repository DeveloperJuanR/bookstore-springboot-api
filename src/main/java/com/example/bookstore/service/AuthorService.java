package com.example.bookstore.service;

import com.example.bookstore.model.Author;
import com.example.bookstore.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    // Create a new author
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    // Retrieve author by ID
    public Optional<Author> getAuthorById(Long authorId) {
        return authorRepository.findById(authorId);  // Returns Optional<Author>
    }
}
