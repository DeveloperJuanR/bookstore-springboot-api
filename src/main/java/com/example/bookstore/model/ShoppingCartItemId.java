package com.example.bookstore.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ShoppingCartItemId implements Serializable {
    @Column(name = "cart_id")
    private Long cartId;
    @Column(name = "ISBN")
    private String ISBN;

    public ShoppingCartItemId() {}

    public ShoppingCartItemId(Long cartId, String ISBN) {
        this.cartId = cartId;
        this.ISBN = ISBN;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String isbn) {
        this.ISBN = isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingCartItemId)) return false;
        ShoppingCartItemId that = (ShoppingCartItemId) o;
        return cartId.equals(that.cartId) && ISBN.equals(that.ISBN);
    }

    @Override
    public int hashCode() {
        return 31 * cartId.hashCode() + ISBN.hashCode();
    }
}
