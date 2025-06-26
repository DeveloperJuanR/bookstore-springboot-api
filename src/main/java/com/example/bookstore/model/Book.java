package com.example.bookstore.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @Column(name = "isbn") // Map to lowercase "isbn" as in the database
    private String isbn;  // Change to lowercase "isbn"

    private String title;
    private String description;
    private double price;
    private String genre;
    private int yearPublished;
    private int copiesSold;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "publisher_id")
    @ManyToOne
    @JoinColumn(name = "publisher_publisher_id")
    private Publisher publisher;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ShoppingCartItem> cartItems;

    // Getter and Setter
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public int getCopiesSold() {
        return copiesSold;
    }

    public void setCopiesSold(int copiesSold) {
        this.copiesSold = copiesSold;
    }

    @Column(name = "publisher_id")
    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}