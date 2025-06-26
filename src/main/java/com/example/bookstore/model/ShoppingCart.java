package com.example.bookstore.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile user;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ShoppingCartItem> items;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public Set<ShoppingCartItem> getItems() {
        return items;
    }

    public void setItems(Set<ShoppingCartItem> items) {
        this.items = items;
    }
}

