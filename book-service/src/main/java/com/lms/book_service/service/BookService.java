package com.lms.book_service.service;

import com.lms.book_service.entity.Book;
import java.util.List;

public interface BookService {
	List<Book> getAllBooks();
	Book getBookById(Long id);
	List<Book> getAvailableBooks();
	List<Book> searchBooks(String title, String author);
	Book addBook(Book book);
	Book updateBook(Long id, Book book);
	void deleteBook(Long id);
}