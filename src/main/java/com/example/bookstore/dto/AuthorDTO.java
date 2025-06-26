package com.example.bookstore.dto;

import com.example.bookstore.model.Author;

public class AuthorDTO {

    private Long authorId;
    private String firstName;
    private String lastName;
    private String biography;

    // Constructor to convert from Author model to AuthorDTO
    public AuthorDTO(Author author) {
        this.authorId = author.getAuthorId();
        this.firstName = author.getFirstName();
        this.lastName = author.getLastName();
        this.biography = author.getBiography();
    }

    // Getters and setters
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
}
