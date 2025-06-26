package com.example.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDTO extends RepresentationModel<BookDTO> {
    @Id
    private String ISBN;
    private String title;
    private String genre;

    private double price;

    @Column(name = "copies_sold")
    private int copiesSold;

    public BookDTO(String ISBN, String title) {
        this.ISBN = ISBN;
        this.title = title;
    }
    public BookDTO(String ISBN, String title, String genre) {
        this.ISBN = ISBN;
        this.title = title;
        this.genre = genre;
    }

    public BookDTO(String ISBN, String title, double price) {
        this.ISBN = ISBN;
        this.title = title;
        this.price = price;
    }

    public BookDTO(String ISBN, String title, int copiesSold) {
        this.ISBN = ISBN;
        this.title = title;
        this.copiesSold = copiesSold;
    }


    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    @Column(name = "genre")
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getCopiesSold() {
        return copiesSold;
    }

    public void setCopiesSold(int copiesSold) {
        this.copiesSold = copiesSold;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}