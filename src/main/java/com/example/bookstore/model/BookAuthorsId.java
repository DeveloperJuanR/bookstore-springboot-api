package com.example.bookstore.model;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

public class BookAuthorsId implements Serializable {
    @Column(name = "isbn")
    private String ISBN;

    @Column(name = "author_id") // Match column name in DB
    private Long authorId;

    // Default constructor
    public BookAuthorsId() {}

    // Parameterized constructor
    public BookAuthorsId(String ISBN, Long authorId) {
        this.ISBN = ISBN;
        this.authorId = authorId;
    }

    // Getters and Setters
    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookAuthorsId that = (BookAuthorsId) o;
        return Objects.equals(ISBN, that.ISBN) && Objects.equals(authorId, that.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ISBN, authorId);
    }
}
