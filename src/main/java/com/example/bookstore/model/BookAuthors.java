package com.example.bookstore.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "book_authors")
public class BookAuthors implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private BookAuthorsId id;

    @ManyToOne
    @MapsId("authorId")  // Maps the authorId from BookAuthorsId
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @ManyToOne
    @MapsId("isbn")  // Maps the ISBN from BookAuthorsId
    @JoinColumn(name = "isbn", referencedColumnName = "isbn", nullable = false) // updated to match "isbn"
    private Book book;

    // Constructors
    public BookAuthors() {}

    public BookAuthors(Author author, Book book) {
        this.author = author;
        this.book = book;
        this.id = new BookAuthorsId(book.getIsbn(), author.getAuthorId()); // updated to getIsbn()
    }

    // Getters and Setters
    public BookAuthorsId getId() {
        return id;
    }

    public void setId(BookAuthorsId id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
