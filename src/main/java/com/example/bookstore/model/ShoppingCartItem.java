package com.example.bookstore.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "shopping_cart_items")
public class ShoppingCartItem implements Serializable {

    @EmbeddedId
    private ShoppingCartItemId id;

    @ManyToOne
    @MapsId("cartId") // This refers to the cartId field in ShoppingCartItemId
    @JoinColumn(name = "cart_id")
    private ShoppingCart shoppingCart;

    @ManyToOne
    @MapsId("ISBN") // This refers to the ISBN field in ShoppingCartItemId
    @JoinColumn(name = "ISBN")
    private Book book;

    private int quantity;

    // Getters and setters
    public ShoppingCartItemId getId() {
        return id;
    }

    public void setId(ShoppingCartItemId id) {
        this.id = id;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

