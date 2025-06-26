package com.example.bookstore.controller;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.dto.BookRatingDTO;
import com.example.bookstore.model.Book;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.RatingsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private RatingsService ratingsService;

    @Autowired
    private ObjectMapper objectMapper;

    private BookDTO bookDTO;
    private Book book;
    private List<BookDTO> bookDTOList;

    @BeforeEach
    void setUp() {
        // Setup test data
        bookDTO = new BookDTO();
        bookDTO.setIsbn("9780123456789");
        bookDTO.setTitle("Test Book");
        bookDTO.setPrice(BigDecimal.valueOf(29.99));
        bookDTO.setGenre("Technology");

        book = new Book();
        book.setIsbn("9780123456789");
        book.setTitle("Test Book");
        book.setPrice(BigDecimal.valueOf(29.99));
        book.setGenre("Technology");
        book.setDescription("A test book for testing purposes");

        bookDTOList = Arrays.asList(bookDTO);
    }

    @Test
    void getAllBooks_ShouldReturnListOfBooks() throws Exception {
        // Given
        when(bookService.getAllBooks()).thenReturn(bookDTOList);

        // When & Then
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].isbn").value("9780123456789"))
                .andExpect(jsonPath("$[0].title").value("Test Book"))
                .andExpect(jsonPath("$[0].price").value(29.99))
                .andExpect(jsonPath("$[0].genre").value("Technology"));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void getBooksByGenre_ShouldReturnBooksOfSpecificGenre() throws Exception {
        // Given
        String genre = "Technology";
        when(bookService.getBooksByGenre(genre)).thenReturn(bookDTOList);

        // When & Then
        mockMvc.perform(get("/api/books/genre/{genre}", genre))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].genre").value("Technology"));

        verify(bookService, times(1)).getBooksByGenre(genre);
    }

    @Test
    void getBooksByRating_ShouldReturnBooksWithMinimumRating() throws Exception {
        // Given
        Integer rating = 4;
        BookRatingDTO ratingDTO = new BookRatingDTO();
        ratingDTO.setIsbn("9780123456789");
        ratingDTO.setTitle("Test Book");
        ratingDTO.setAverageRating(4.5);
        
        List<BookRatingDTO> ratingDTOList = Arrays.asList(ratingDTO);
        when(ratingsService.getBooksByRating(rating)).thenReturn(ratingDTOList);

        // When & Then
        mockMvc.perform(get("/api/books/rating/{rating}", rating))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].isbn").value("9780123456789"))
                .andExpect(jsonPath("$[0].averageRating").value(4.5));

        verify(ratingsService, times(1)).getBooksByRating(rating);
    }

    @Test
    void getBookByIsbn_WhenBookExists_ShouldReturnBook() throws Exception {
        // Given
        String isbn = "9780123456789";
        when(bookService.getBookByISBN(isbn)).thenReturn(book);

        // When & Then
        mockMvc.perform(get("/api/books/{isbn}", isbn))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isbn").value(isbn))
                .andExpect(jsonPath("$.title").value("Test Book"));

        verify(bookService, times(1)).getBookByISBN(isbn);
    }

    @Test
    void getBookByIsbn_WhenBookNotExists_ShouldReturnNotFound() throws Exception {
        // Given
        String isbn = "9999999999999";
        when(bookService.getBookByISBN(isbn)).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/api/books/{isbn}", isbn))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).getBookByISBN(isbn);
    }

    @Test
    void getTopSellers_ShouldReturnTopSellingBooks() throws Exception {
        // Given
        when(bookService.getTopSellers()).thenReturn(bookDTOList);

        // When & Then
        mockMvc.perform(get("/api/books/top-sellers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].isbn").value("9780123456789"));

        verify(bookService, times(1)).getTopSellers();
    }

    @Test
    void addBook_ShouldCreateNewBook() throws Exception {
        // Given
        doNothing().when(bookService).addBook(any(Book.class));

        // When & Then
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated());

        verify(bookService, times(1)).addBook(any(Book.class));
    }

    @Test
    void discountBooksByPublisher_ShouldApplyDiscount() throws Exception {
        // Given
        Double percentage = 10.0;
        Long publisherId = 1L;
        doNothing().when(bookService).discountBooksByPublisher(percentage, publisherId);

        // When & Then
        mockMvc.perform(patch("/api/books/discount")
                .param("percentage", percentage.toString())
                .param("publisherId", publisherId.toString()))
                .andExpect(status().isOk());

        verify(bookService, times(1)).discountBooksByPublisher(percentage, publisherId);
    }

    @Test
    void getBooksByPublisherId_ShouldReturnPublisherBooks() throws Exception {
        // Given
        Long publisherId = 1L;
        List<Book> books = Arrays.asList(book);
        when(bookService.getBooksByPublisherId(publisherId)).thenReturn(books);

        // When & Then
        mockMvc.perform(get("/api/books/publisher/{publisherId}", publisherId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].isbn").value("9780123456789"));

        verify(bookService, times(1)).getBooksByPublisherId(publisherId);
    }
} 