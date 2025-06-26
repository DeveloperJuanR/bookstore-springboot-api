package com.example.bookstore.repository;

import com.example.bookstore.model.ShoppingCartItem;
import com.example.bookstore.model.ShoppingCartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, ShoppingCartItemId> {

    // Find all ShoppingCartItems by the User's userId
    List<ShoppingCartItem> findByShoppingCartUserUserId(Long userId);

    // Find a ShoppingCartItem by cartId and the ISBN of the Book
    Optional<ShoppingCartItem> findByShoppingCartCartIdAndBookIsbn(Long cartId, String isbn);

    // Find all ShoppingCartItems by cartId
    List<ShoppingCartItem> findByShoppingCartCartId(Long cartId);
}
