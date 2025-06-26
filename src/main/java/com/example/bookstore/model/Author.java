package com.example.bookstore.model;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId; // Primary key

    private String firstName;
    private String lastName;
    private String biography;

    @OneToMany(mappedBy = "author")
    private List<BookAuthors> bookAuthors;

    // Getters and Setters
    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<BookAuthors> getBookAuthors() {
        return bookAuthors;
    }

    public void setBookAuthors(List<BookAuthors> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }
}
