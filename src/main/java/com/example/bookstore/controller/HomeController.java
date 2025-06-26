package com.example.bookstore.controller;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<RepresentationModel> getRoot() {

        RepresentationModel homeResource = new RepresentationModel();

        Link shoppingCartRootLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ShoppingCartController.class).getShoppingCartRoot()
        ).withRel("shopping-cart-root");

        homeResource.add(shoppingCartRootLink);

        return ResponseEntity.ok(homeResource);

    }
}
