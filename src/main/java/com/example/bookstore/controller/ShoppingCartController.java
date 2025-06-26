 package com.example.bookstore.controller;

import com.example.bookstore.dto.ShoppingCartItemDTO;
import com.example.bookstore.dto.ShoppingCartSubtotalResource;
import com.example.bookstore.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.hateoas.EntityModel;
import java.util.Map;


@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    // Root
    @GetMapping
    public ResponseEntity<RepresentationModel> getShoppingCartRoot() {
        RepresentationModel rootResource = new RepresentationModel<>();


        Link myCartLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ShoppingCartController.class).getBooksInShoppingCart(null) // Add userID handling
        ).withRel("shopping-cart-books");


        Link subtotalLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ShoppingCartController.class).getShoppingCartSubtotal(null)
        ).withRel("shopping-cart-subtotal");


        Link rootLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(HomeController.class).getRoot()
        ).withRel("root");


        rootResource.add(myCartLink);
        rootResource.add(subtotalLink);
        rootResource.add(rootLink);

        return ResponseEntity.ok(rootResource);
    }


    // GET: all user books in shopping cart ----------------------------------------------------------------------------
    @GetMapping("/{userId}/books")
    public List<EntityModel<ShoppingCartItemDTO>> getBooksInShoppingCart(@PathVariable Long userId) {
        return shoppingCartService.getBooksInShoppingCart(userId);
    }


    // GET: shopping cart subtotal -------------------------------------------------------------------------------------
    @GetMapping("/{userId}/subtotal")
    public ResponseEntity<ShoppingCartSubtotalResource> getShoppingCartSubtotal(@PathVariable Long userId) {
        if (userId == null || userId <= 0) {
            return ResponseEntity.badRequest().build();
        }
        ShoppingCartSubtotalResource resource = shoppingCartService.getShoppingCartSubtotal(userId);
        return ResponseEntity.ok(resource);
    }


    // POST: add book to user shopping cart ----------------------------------------------------------------------------
    @PostMapping("/{userId}/add-book")
    public ResponseEntity<Void> addBookToShoppingCart(
            @PathVariable Long userId,
            @RequestBody Map<String, String> request) {
        String isbn = request.get("isbn");
        shoppingCartService.addBookToShoppingCart(userId, isbn);

        return ResponseEntity.ok().build();
    }


    // DELETE: remove book from user shopping cart ---------------------------------------------------------------------
    @DeleteMapping("/{userId}/remove-book")
    public ResponseEntity<Void> removeBookFromShoppingCart(
            @PathVariable Long userId,
            @RequestBody Map<String, String> request) {
        String isbn = request.get("isbn");
        shoppingCartService.removeBookFromShoppingCart(userId, isbn);

        return ResponseEntity.ok().build();
    }

}


