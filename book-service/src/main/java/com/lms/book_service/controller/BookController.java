package com.lms.book_service.controller;

import com.lms.book_service.entity.Book;
import com.lms.book_service.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
	
	private final BookService service;
	
	public BookController(BookService service) {
		this.service = service;
	}
	
	@GetMapping
	public List<Book> getAll() {
		return service.getAllBooks();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Long id) {
		try {
			Book book = service.getBookById(id);
			return ResponseEntity.ok(book);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/available")
	public List<Book> getAvailableBooks() {
		return service.getAvailableBooks();
	}
	
	@GetMapping("/search")
	public List<Book> searchBooks(@RequestParam(required = false) String title,
	                             @RequestParam(required = false) String author) {
		return service.searchBooks(title, author);
	}
	
	@PostMapping
	public Book add(@RequestBody Book book) {
		return service.addBook(book);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
		try {
			Book updatedBook = service.updateBook(id, book);
			return ResponseEntity.ok(updatedBook);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		try {
			service.deleteBook(id);
			return ResponseEntity.noContent().build();
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}
}