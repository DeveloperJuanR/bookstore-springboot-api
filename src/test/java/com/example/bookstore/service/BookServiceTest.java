package com.example.bookstore.service;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book1;
    private Book book2;
    private List<Book> bookList;

    @BeforeEach
    void setUp() {
        book1 = new Book();
        book1.setIsbn("9780123456789");
        book1.setTitle("Java Programming");
        book1.setPrice(BigDecimal.valueOf(49.99));
        book1.setGenre("Technology");
        book1.setCopiesSold(1000);

        book2 = new Book();
        book2.setIsbn("9780987654321");
        book2.setTitle("Spring Boot Guide");
        book2.setPrice(BigDecimal.valueOf(39.99));
        book2.setGenre("Technology");
        book2.setCopiesSold(750);

        bookList = Arrays.asList(book1, book2);
    }

    @Test
    void getAllBooks_ShouldReturnAllBooks() {
        // Given
        when(bookRepository.findAll()).thenReturn(bookList);

        // When
        List<BookDTO> result = bookService.getAllBooks();

        // Then
        assertEquals(2, result.size());
        assertEquals("Java Programming", result.get(0).getTitle());
        assertEquals("Spring Boot Guide", result.get(1).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBooksByGenre_ShouldReturnBooksOfSpecificGenre() {
        // Given
        String genre = "Technology";
        when(bookRepository.findByGenre(genre)).thenReturn(bookList);

        // When
        List<BookDTO> result = bookService.getBooksByGenre(genre);

        // Then
        assertEquals(2, result.size());
        result.forEach(book -> assertEquals("Technology", book.getGenre()));
        verify(bookRepository, times(1)).findByGenre(genre);
    }

    @Test
    void getBookByISBN_WhenBookExists_ShouldReturnBook() {
        // Given
        String isbn = "9780123456789";
        when(bookRepository.findById(isbn)).thenReturn(Optional.of(book1));

        // When
        Book result = bookService.getBookByISBN(isbn);

        // Then
        assertNotNull(result);
        assertEquals(isbn, result.getIsbn());
        assertEquals("Java Programming", result.getTitle());
        verify(bookRepository, times(1)).findById(isbn);
    }

    @Test
    void getBookByISBN_WhenBookNotExists_ShouldReturnNull() {
        // Given
        String isbn = "9999999999999";
        when(bookRepository.findById(isbn)).thenReturn(Optional.empty());

        // When
        Book result = bookService.getBookByISBN(isbn);

        // Then
        assertNull(result);
        verify(bookRepository, times(1)).findById(isbn);
    }

    @Test
    void getTopSellers_ShouldReturnBooksSortedByCopiesSold() {
        // Given
        when(bookRepository.findTop10ByOrderByCopiesSoldDesc()).thenReturn(bookList);

        // When
        List<BookDTO> result = bookService.getTopSellers();

        // Then
        assertEquals(2, result.size());
        assertEquals("Java Programming", result.get(0).getTitle()); // Higher copies sold
        verify(bookRepository, times(1)).findTop10ByOrderByCopiesSoldDesc();
    }

    @Test
    void addBook_ShouldSaveBookSuccessfully() {
        // Given
        Book newBook = new Book();
        newBook.setIsbn("9781111111111");
        newBook.setTitle("New Book");
        when(bookRepository.save(newBook)).thenReturn(newBook);

        // When
        bookService.addBook(newBook);

        // Then
        verify(bookRepository, times(1)).save(newBook);
    }

    @Test
    void getBooksByPublisherId_ShouldReturnPublisherBooks() {
        // Given
        Long publisherId = 1L;
        when(bookRepository.findByPublisherId(publisherId)).thenReturn(bookList);

        // When
        List<Book> result = bookService.getBooksByPublisherId(publisherId);

        // Then
        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findByPublisherId(publisherId);
    }

    @Test
    void discountBooksByPublisher_ShouldApplyDiscountCorrectly() {
        // Given
        Double discountPercentage = 10.0;
        Long publisherId = 1L;
        when(bookRepository.findByPublisherId(publisherId)).thenReturn(bookList);

        // When
        bookService.discountBooksByPublisher(discountPercentage, publisherId);

        // Then
        verify(bookRepository, times(1)).findByPublisherId(publisherId);
        verify(bookRepository, times(1)).saveAll(anyList());
        
        // Verify prices were updated (assuming the discount logic reduces price by percentage)
        assertEquals(BigDecimal.valueOf(44.99).setScale(2), book1.getPrice().setScale(2));
        assertEquals(BigDecimal.valueOf(35.99).setScale(2), book2.getPrice().setScale(2));
    }
} 