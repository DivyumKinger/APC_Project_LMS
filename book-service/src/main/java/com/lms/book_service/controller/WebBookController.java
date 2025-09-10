package com.lms.book_service.controller;

import com.lms.book_service.entity.Book;
import com.lms.book_service.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/books")
public class WebBookController {
    
    private final BookService bookService;
    
    public WebBookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    @GetMapping
    public String booksPage(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("newBook", new Book());
        return "books";
    }
    
    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        try {
            bookService.addBook(book);
            redirectAttributes.addFlashAttribute("successMessage", "Book added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding book: " + e.getMessage());
        }
        return "redirect:/books";
    }
    
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        try {
            bookService.updateBook(id, book);
            redirectAttributes.addFlashAttribute("successMessage", "Book updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating book: " + e.getMessage());
        }
        return "redirect:/books";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteBook(id);
            redirectAttributes.addFlashAttribute("successMessage", "Book deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting book: " + e.getMessage());
        }
        return "redirect:/books";
    }
}
