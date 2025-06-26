package com.example.bookstore.controller;

import com.example.bookstore.dto.ShoppingCartItemDTO;
import com.example.bookstore.dto.ShoppingCartSubtotalResource;
import com.example.bookstore.service.ShoppingCartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShoppingCartController.class)
class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ObjectMapper objectMapper;

    private ShoppingCartItemDTO cartItemDTO;
    private ShoppingCartSubtotalResource subtotalResource;

    @BeforeEach
    void setUp() {
        cartItemDTO = new ShoppingCartItemDTO();
        cartItemDTO.setIsbn("9780123456789");
        cartItemDTO.setTitle("Test Book");
        cartItemDTO.setPrice(BigDecimal.valueOf(29.99));
        cartItemDTO.setQuantity(2);

        subtotalResource = new ShoppingCartSubtotalResource();
        subtotalResource.setSubtotal(BigDecimal.valueOf(59.98));
        subtotalResource.setTotalItems(2);
    }

    @Test
    void getShoppingCartRoot_ShouldReturnRootWithLinks() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/shopping-cart"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$._links").exists());
    }

    @Test
    void getBooksInShoppingCart_ShouldReturnUserCartItems() throws Exception {
        // Given
        Long userId = 1L;
        EntityModel<ShoppingCartItemDTO> entityModel = EntityModel.of(cartItemDTO);
        List<EntityModel<ShoppingCartItemDTO>> cartItems = Arrays.asList(entityModel);
        
        when(shoppingCartService.getBooksInShoppingCart(userId)).thenReturn(cartItems);

        // When & Then
        mockMvc.perform(get("/api/shopping-cart/{userId}/books", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(shoppingCartService, times(1)).getBooksInShoppingCart(userId);
    }

    @Test
    void getShoppingCartSubtotal_WithValidUserId_ShouldReturnSubtotal() throws Exception {
        // Given
        Long userId = 1L;
        when(shoppingCartService.getShoppingCartSubtotal(userId)).thenReturn(subtotalResource);

        // When & Then
        mockMvc.perform(get("/api/shopping-cart/{userId}/subtotal", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.subtotal").value(59.98))
                .andExpect(jsonPath("$.totalItems").value(2));

        verify(shoppingCartService, times(1)).getShoppingCartSubtotal(userId);
    }

    @Test
    void getShoppingCartSubtotal_WithInvalidUserId_ShouldReturnBadRequest() throws Exception {
        // Given
        Long invalidUserId = -1L;

        // When & Then
        mockMvc.perform(get("/api/shopping-cart/{userId}/subtotal", invalidUserId))
                .andExpect(status().isBadRequest());

        verify(shoppingCartService, never()).getShoppingCartSubtotal(any());
    }

    @Test
    void addBookToShoppingCart_ShouldAddBookSuccessfully() throws Exception {
        // Given
        Long userId = 1L;
        String isbn = "9780123456789";
        Map<String, String> request = new HashMap<>();
        request.put("isbn", isbn);

        doNothing().when(shoppingCartService).addBookToShoppingCart(userId, isbn);

        // When & Then
        mockMvc.perform(post("/api/shopping-cart/{userId}/add-book", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(shoppingCartService, times(1)).addBookToShoppingCart(userId, isbn);
    }

    @Test
    void removeBookFromShoppingCart_ShouldRemoveBookSuccessfully() throws Exception {
        // Given
        Long userId = 1L;
        String isbn = "9780123456789";
        Map<String, String> request = new HashMap<>();
        request.put("isbn", isbn);

        doNothing().when(shoppingCartService).removeBookFromShoppingCart(userId, isbn);

        // When & Then
        mockMvc.perform(delete("/api/shopping-cart/{userId}/remove-book", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(shoppingCartService, times(1)).removeBookFromShoppingCart(userId, isbn);
    }
} 