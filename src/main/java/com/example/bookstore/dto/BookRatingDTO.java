package com.example.bookstore.dto;

public class BookRatingDTO {
    private String isbn;
    private String title;
    private Double averageRating;

    public BookRatingDTO(String isbn, String title, Double averageRating) {
        this.isbn = isbn;
        this.title = title;
        this.averageRating = averageRating;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}
