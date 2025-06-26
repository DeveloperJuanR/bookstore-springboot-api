package com.example.bookstore.service;

import com.example.bookstore.controller.HomeController;
import com.example.bookstore.controller.ShoppingCartController;
import com.example.bookstore.dto.ShoppingCartItemDTO;
import com.example.bookstore.dto.ShoppingCartSubtotalResource;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.ShoppingCartItem;
import com.example.bookstore.model.ShoppingCartItemId;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.ShoppingCartItemRepository;
import com.example.bookstore.repository.ShoppingCartRepository;
import com.example.bookstore.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository cartRepository;

    @Autowired
    private ShoppingCartItemRepository cartItemRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;


    // Get all books in shopping cart ----------------------------------------------------------------------------------
    public List<EntityModel<ShoppingCartItemDTO>> getBooksInShoppingCart(Long userID) {
        ShoppingCart shoppingCart = cartRepository.findByUserUserId(userID)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found"));

        List<ShoppingCartItem> cartItems = cartItemRepository.findByShoppingCartCartId(shoppingCart.getCartId());

        return cartItems.stream()
                .map(cartItem -> {
                    Book book = cartItem.getBook();
                    Integer quantity = cartItem.getQuantity(); // new

                    ShoppingCartItemDTO shoppingCartItemDTO = new ShoppingCartItemDTO(book, quantity);

                    Link selfLink = WebMvcLinkBuilder.linkTo(
                            WebMvcLinkBuilder.methodOn(ShoppingCartController.class).getBooksInShoppingCart(userID)
                    ).withRel("shopping-cart-books");

                    Link cartSubtotalLink = WebMvcLinkBuilder.linkTo(
                            WebMvcLinkBuilder.methodOn(ShoppingCartController.class).getShoppingCartSubtotal(userID)
                    ).withRel("shopping-cart-subtotal");

                    Link shoppingCartRootLink = WebMvcLinkBuilder.linkTo(
                            WebMvcLinkBuilder.methodOn(ShoppingCartController.class).getShoppingCartRoot()
                    ).withRel("shopping-cart-root");

                    Link rootLink = WebMvcLinkBuilder.linkTo(
                            WebMvcLinkBuilder.methodOn(HomeController.class).getRoot()
                    ).withRel("root");

                    return EntityModel.of(shoppingCartItemDTO, selfLink, cartSubtotalLink,
                            shoppingCartRootLink, rootLink);
                })
                .collect(Collectors.toList());
    }


    // Add book to shopping cart ---------------------------------------------------------------------------------------
    public void addBookToShoppingCart(Long userId, String isbn) {
        ShoppingCart cart = cartRepository.findByUserUserId(userId)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUser(userProfileRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found")));
                    return cartRepository.save(newCart);
                });

        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        ShoppingCartItem item = cartItemRepository.findByShoppingCartCartIdAndBookIsbn(cart.getCartId(), isbn)
                .orElseGet(() -> {
                    ShoppingCartItem newItem = new ShoppingCartItem();
                    ShoppingCartItemId newId = new ShoppingCartItemId();
                    newId.setCartId(cart.getCartId());
                    newId.setISBN(isbn);
                    newItem.setId(newId);
                    newItem.setShoppingCart(cart);
                    newItem.setBook(book);
                    return newItem;
                });

        item.setQuantity(item.getQuantity() + 1);
        cartItemRepository.save(item);
    }


    // Delete a book from shopping cart, quantity 0 --------------------------------------------------------------------
    public void removeBookFromShoppingCart(Long userId, String isbn) {
        ShoppingCart shoppingCart = cartRepository.findByUserUserId(userId)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found"));

        ShoppingCartItem book = cartItemRepository.findByShoppingCartCartIdAndBookIsbn(shoppingCart.getCartId(), isbn)
                .orElseThrow(() -> new RuntimeException("Book not found in shopping cart"));

        book.setQuantity(book.getQuantity() - 1);
        cartItemRepository.delete(book);
    }


    // Retrieve shopping cart subtotal ---------------------------------------------------------------------------------
    public ShoppingCartSubtotalResource getShoppingCartSubtotal(Long userID) {
        ShoppingCart shoppingCart = cartRepository.findByUserUserId(userID)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found"));

        List<ShoppingCartItem> shoppingCartItems = cartItemRepository
                .findByShoppingCartCartId(shoppingCart.getCartId());

        double subtotal = shoppingCartItems.stream()
                .mapToDouble(cartItem -> cartItem.getBook().getPrice() * cartItem.getQuantity())
                .sum();

        ShoppingCartSubtotalResource resource = new ShoppingCartSubtotalResource(subtotal);


        Link selfLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ShoppingCartController.class).getShoppingCartSubtotal(userID)
        ).withRel("shopping-cart-subtotal");

        Link getBooksLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ShoppingCartController.class).getBooksInShoppingCart(userID)
        ).withRel("shopping-cart-books");

        Link shoppingCartRootLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ShoppingCartController.class).getShoppingCartRoot()
        ).withRel("shopping-cart-root");

        Link rootLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(HomeController.class).getRoot()
        ).withRel("root");


        resource.add(selfLink);
        resource.add(getBooksLink);
        resource.add(shoppingCartRootLink);
        resource.add(rootLink);

        return resource;
    }
}
