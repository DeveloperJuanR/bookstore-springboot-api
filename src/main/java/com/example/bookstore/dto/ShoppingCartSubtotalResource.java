package com.example.bookstore.dto;

import org.springframework.hateoas.RepresentationModel;

public class ShoppingCartSubtotalResource extends RepresentationModel<ShoppingCartSubtotalResource> {
    private Double subtotal;

    public ShoppingCartSubtotalResource(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}
