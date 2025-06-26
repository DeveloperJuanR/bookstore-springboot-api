package com.example.bookstore.integration;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Publisher;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.PublisherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
class BookstoreIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Publisher publisher;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        // Clean up existing data
        bookRepository.deleteAll();
        publisherRepository.deleteAll();

        // Create publisher
        publisher = new Publisher();
        publisher.setName("Tech Books Publishing");
        publisher.setAddress("123 Publisher Street");
        publisher = publisherRepository.save(publisher);

        // Create books
        book1 = new Book();
        book1.setIsbn("9780123456789");
        book1.setTitle("Java Programming Masterclass");
        book1.setDescription("Complete guide to Java programming");
        book1.setPrice(BigDecimal.valueOf(49.99));
        book1.setGenre("Technology");
        book1.setYearPublished((short) 2023);
        book1.setCopiesSold(1000);
        book1.setPublisherId(publisher.getPublisherId());

        book2 = new Book();
        book2.setIsbn("9780987654321");
        book2.setTitle("Spring Boot in Action");
        book2.setDescription("Learn Spring Boot framework");
        book2.setPrice(BigDecimal.valueOf(39.99));
        book2.setGenre("Technology");
        book2.setYearPublished((short) 2023);
        book2.setCopiesSold(750);
        book2.setPublisherId(publisher.getPublisherId());

        bookRepository.save(book1);
        bookRepository.save(book2);
    }

    @Test
    void getAllBooks_ShouldReturnAllBooksInDatabase() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Java Programming Masterclass"))
                .andExpect(jsonPath("$[1].title").value("Spring Boot in Action"));
    }

    @Test
    void getBookByIsbn_ShouldReturnSpecificBook() throws Exception {
        mockMvc.perform(get("/api/books/{isbn}", book1.getIsbn()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isbn").value(book1.getIsbn()))
                .andExpect(jsonPath("$.title").value("Java Programming Masterclass"))
                .andExpect(jsonPath("$.price").value(49.99));
    }

    @Test
    void getBooksByGenre_ShouldReturnFilteredBooks() throws Exception {
        mockMvc.perform(get("/api/books/genre/Technology"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getBooksByPublisher_ShouldReturnPublisherBooks() throws Exception {
        mockMvc.perform(get("/api/books/publisher/{publisherId}", publisher.getPublisherId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getTopSellers_ShouldReturnBooksSortedBySales() throws Exception {
        mockMvc.perform(get("/api/books/top-sellers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Java Programming Masterclass")); // Higher copies sold
    }

    @Test
    void addBook_ShouldCreateNewBookInDatabase() throws Exception {
        Book newBook = new Book();
        newBook.setIsbn("9781234567890");
        newBook.setTitle("Python Data Science");
        newBook.setDescription("Learn data science with Python");
        newBook.setPrice(BigDecimal.valueOf(54.99));
        newBook.setGenre("Technology");
        newBook.setYearPublished((short) 2023);
        newBook.setCopiesSold(0);
        newBook.setPublisherId(publisher.getPublisherId());

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isCreated());

        // Verify book was actually created in database
        mockMvc.perform(get("/api/books/{isbn}", newBook.getIsbn()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Python Data Science"));
    }

    @Test
    void discountBooksByPublisher_ShouldUpdateBookPrices() throws Exception {
        Double discountPercentage = 10.0;

        mockMvc.perform(patch("/api/books/discount")
                .param("percentage", discountPercentage.toString())
                .param("publisherId", publisher.getPublisherId().toString()))
                .andExpect(status().isOk());

        // Verify prices were updated in database
        mockMvc.perform(get("/api/books/{isbn}", book1.getIsbn()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(44.99)); // 49.99 - 10% = 44.99
    }

    @Test
    void getAllPublishers_ShouldReturnAllPublishers() throws Exception {
        mockMvc.perform(get("/api/publishers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Tech Books Publishing"));
    }

    @Test
    void createPublisher_ShouldAddNewPublisher() throws Exception {
        Publisher newPublisher = new Publisher();
        newPublisher.setName("Science Publishers");
        newPublisher.setAddress("456 Science Avenue");

        mockMvc.perform(post("/api/publishers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPublisher)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Science Publishers"));

        // Verify it was actually saved
        mockMvc.perform(get("/api/publishers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void completeWorkflow_AddBookThenRetrieve_ShouldWork() throws Exception {
        // Step 1: Create a new book
        Book newBook = new Book();
        newBook.setIsbn("9999999999999");
        newBook.setTitle("Complete Workflow Test");
        newBook.setPrice(BigDecimal.valueOf(29.99));
        newBook.setGenre("Testing");

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isCreated());

        // Step 2: Retrieve the book
        mockMvc.perform(get("/api/books/{isbn}", newBook.getIsbn()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Complete Workflow Test"));

        // Step 3: Get books by genre
        mockMvc.perform(get("/api/books/genre/Testing"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value("Complete Workflow Test"));
    }
} 