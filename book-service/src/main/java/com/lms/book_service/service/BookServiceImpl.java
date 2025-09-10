package com.lms.book_service.service;

import com.lms.book_service.entity.Book;
import com.lms.book_service.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
	
	private final BookRepository repo;
	
	public BookServiceImpl(BookRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public List<Book> getAllBooks() {
		return repo.findAll();
	}
	
	@Override
	public Book getBookById(Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
	}
	
	@Override
	public List<Book> getAvailableBooks() {
		return repo.findByAvailableCopiesGreaterThan(0);
	}
	
	@Override
	public List<Book> searchBooks(String title, String author) {
		return repo.findByTitleAndAuthorContainingIgnoreCase(title, author);
	}
	
	@Override
	public Book addBook(Book book) {
		return repo.save(book);
	}
	
	@Override
	public Book updateBook(Long id, Book book) {
		Book existingBook = getBookById(id);
		existingBook.setTitle(book.getTitle());
		existingBook.setAuthor(book.getAuthor());
		existingBook.setAvailableCopies(book.getAvailableCopies());
		if (book.getAvailableCopies() > 0) {
			existingBook.setStatus("AVAILABLE");
		}
		return repo.save(existingBook);
	}
	
	@Override
	public void deleteBook(Long id) {
		if (!repo.existsById(id)) {
			throw new RuntimeException("Book not found with id: " + id);
		}
		repo.deleteById(id);
	}
}
