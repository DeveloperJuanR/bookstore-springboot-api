package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    private Book book1;
    private Book book2;
    private Publisher publisher;

    @BeforeEach
    void setUp() {
        // Create and save publisher
        publisher = new Publisher();
        publisher.setName("Tech Publishers");
        publisher.setAddress("123 Tech Street");
        publisher = entityManager.persistAndFlush(publisher);

        // Create books
        book1 = new Book();
        book1.setIsbn("9780123456789");
        book1.setTitle("Java Programming");
        book1.setDescription("Complete guide to Java programming");
        book1.setPrice(BigDecimal.valueOf(49.99));
        book1.setGenre("Technology");
        book1.setYearPublished((short) 2023);
        book1.setCopiesSold(1000);
        book1.setPublisherId(publisher.getPublisherId());

        book2 = new Book();
        book2.setIsbn("9780987654321");
        book2.setTitle("Python Guide");
        book2.setDescription("Learn Python programming");
        book2.setPrice(BigDecimal.valueOf(39.99));
        book2.setGenre("Technology");
        book2.setYearPublished((short) 2023);
        book2.setCopiesSold(750);
        book2.setPublisherId(publisher.getPublisherId());

        // Save books
        entityManager.persistAndFlush(book1);
        entityManager.persistAndFlush(book2);
    }

    @Test
    void findById_WhenBookExists_ShouldReturnBook() {
        // When
        Optional<Book> result = bookRepository.findById("9780123456789");

        // Then
        assertTrue(result.isPresent());
        assertEquals("Java Programming", result.get().getTitle());
        assertEquals(BigDecimal.valueOf(49.99), result.get().getPrice());
    }

    @Test
    void findById_WhenBookNotExists_ShouldReturnEmpty() {
        // When
        Optional<Book> result = bookRepository.findById("9999999999999");

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void findByGenre_ShouldReturnBooksOfSpecificGenre() {
        // When
        List<Book> result = bookRepository.findByGenre("Technology");

        // Then
        assertEquals(2, result.size());
        result.forEach(book -> assertEquals("Technology", book.getGenre()));
    }

    @Test
    void findByGenre_WhenNoBooks_ShouldReturnEmptyList() {
        // When
        List<Book> result = bookRepository.findByGenre("Fiction");

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void findByPublisherId_ShouldReturnPublisherBooks() {
        // When
        List<Book> result = bookRepository.findByPublisherId(publisher.getPublisherId());

        // Then
        assertEquals(2, result.size());
        result.forEach(book -> assertEquals(publisher.getPublisherId(), book.getPublisherId()));
    }

    @Test
    void findTop10ByOrderByCopiesSoldDesc_ShouldReturnTopSellers() {
        // When
        List<Book> result = bookRepository.findTop10ByOrderByCopiesSoldDesc();

        // Then
        assertEquals(2, result.size());
        assertEquals("Java Programming", result.get(0).getTitle()); // Higher copies sold
        assertEquals("Python Guide", result.get(1).getTitle());
        assertTrue(result.get(0).getCopiesSold() >= result.get(1).getCopiesSold());
    }

    @Test
    void findBooksByAuthorId_ShouldReturnAuthorBooks() {
        // Given
        Long authorId = 1L;
        book1.setAuthorId(authorId);
        entityManager.persistAndFlush(book1);

        // When
        List<Book> result = bookRepository.findBooksByAuthorId(authorId);

        // Then
        assertEquals(1, result.size());
        assertEquals("Java Programming", result.get(0).getTitle());
    }

    @Test
    void save_ShouldPersistBookSuccessfully() {
        // Given
        Book newBook = new Book();
        newBook.setIsbn("9781111111111");
        newBook.setTitle("New Programming Book");
        newBook.setPrice(BigDecimal.valueOf(59.99));
        newBook.setGenre("Technology");

        // When
        Book savedBook = bookRepository.save(newBook);

        // Then
        assertNotNull(savedBook);
        assertEquals("9781111111111", savedBook.getIsbn());
        assertEquals("New Programming Book", savedBook.getTitle());

        // Verify it's actually saved
        Optional<Book> retrievedBook = bookRepository.findById("9781111111111");
        assertTrue(retrievedBook.isPresent());
        assertEquals("New Programming Book", retrievedBook.get().getTitle());
    }

    @Test
    void findAll_ShouldReturnAllBooks() {
        // When
        List<Book> result = bookRepository.findAll();

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void deleteById_ShouldRemoveBook() {
        // When
        bookRepository.deleteById("9780123456789");

        // Then
        Optional<Book> result = bookRepository.findById("9780123456789");
        assertFalse(result.isPresent());

        // Verify other book still exists
        Optional<Book> otherBook = bookRepository.findById("9780987654321");
        assertTrue(otherBook.isPresent());
    }
} 