package com.example.bookstore.model;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "ratings")
public class Ratings implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private RatingsId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile user;

    @ManyToOne
    @MapsId("ISBN")
    @JoinColumn(name = "isbn", referencedColumnName = "ISBN", nullable = false)
    private Book book;

    private int rating;

    public Ratings() {

    }

    public Ratings(RatingsId id, UserProfile user, Book book, int rating) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.rating = rating;
    }

    public RatingsId getId() {
        return id;
    }

    public void setId(RatingsId id) {
        this.id = id;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
