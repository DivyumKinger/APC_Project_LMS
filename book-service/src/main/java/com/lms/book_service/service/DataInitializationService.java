package com.lms.book_service.service;

import com.lms.book_service.entity.Book;
import com.lms.book_service.repository.BookRepository;
import com.lms.book_service.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class DataInitializationService implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private IssueRepository issueRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("Starting data initialization...");
        
        // Clear existing data
        clearExistingData();
        
        // Add fresh sample data
        addSampleBooks();
        
        System.out.println("Data initialization completed successfully!");
    }

    private void clearExistingData() {
        System.out.println("Clearing existing data...");
        
        // Delete all issues first (due to foreign key constraints)
        issueRepository.deleteAll();
        System.out.println("Cleared all issues");
        
        // Delete all books
        bookRepository.deleteAll();
        System.out.println("Cleared all books");
    }

    private void addSampleBooks() {
        System.out.println("Adding sample books...");
        
        List<Book> sampleBooks = Arrays.asList(
            createBook("Java: The Complete Reference", "Herbert Schildt", 5),
            createBook("Spring Boot in Action", "Craig Walls", 3),
            createBook("Clean Code", "Robert C. Martin", 4),
            createBook("Design Patterns", "Gang of Four", 2),
            createBook("Effective Java", "Joshua Bloch", 3),
            createBook("Spring Framework Documentation", "Pivotal Team", 2),
            createBook("MySQL Database Administration", "John Smith", 4),
            createBook("RESTful Web Services", "Leonard Richardson", 3),
            createBook("Microservices Patterns", "Chris Richardson", 2),
            createBook("Docker Deep Dive", "Nigel Poulton", 3),
            createBook("Kubernetes in Action", "Marko Lukša", 2),
            createBook("Maven: The Definitive Guide", "Sonatype Company", 3),
            createBook("Git Pro", "Scott Chacon", 4),
            createBook("Linux Command Line", "William Shotts", 5),
            createBook("JavaScript: The Good Parts", "Douglas Crockford", 3),
            createBook("React: Up & Running", "Stoyan Stefanov", 2),
            createBook("Node.js Design Patterns", "Mario Casciaro", 3),
            createBook("Python Crash Course", "Eric Matthes", 4),
            createBook("Data Structures and Algorithms", "Thomas Cormen", 2),
            createBook("Computer Networks", "Andrew Tanenbaum", 3)
        );

        bookRepository.saveAll(sampleBooks);
        System.out.println("Added " + sampleBooks.size() + " sample books");
    }

    private Book createBook(String title, String author, int availableCopies) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setAvailableCopies(availableCopies);
        book.setTotalQuantity(availableCopies);
        book.setIssued(false); // Fixed: Use setIssued() now that field is named 'issued'
        book.setStatus("AVAILABLE");
        book.setCreatedAt(LocalDateTime.now());
        return book;
    }
}
