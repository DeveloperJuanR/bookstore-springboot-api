package com.example.bookstore.dto;

import com.example.bookstore.model.Book;

public class ShoppingCartItemDTO {
    private Book book;
    private Integer quantity;

    public ShoppingCartItemDTO(Book book, Integer quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
