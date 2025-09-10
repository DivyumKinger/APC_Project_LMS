package com.lms.book_service.controller;

import com.lms.book_service.dto.IssueBookRequest;
import com.lms.book_service.dto.ReturnBookRequest;
import com.lms.book_service.dto.StudentDTO;
import com.lms.book_service.entity.Issue;
import com.lms.book_service.service.BookService;
import com.lms.book_service.service.IssueService;
import com.lms.book_service.service.StudentServiceClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class WebIssueController {
    
    private final IssueService issueService;
    private final BookService bookService;
    private final StudentServiceClient studentServiceClient;
    
    public WebIssueController(IssueService issueService, BookService bookService, StudentServiceClient studentServiceClient) {
        this.issueService = issueService;
        this.bookService = bookService;
        this.studentServiceClient = studentServiceClient;
    }
    
    @GetMapping("/issues")
    public String issuesPage(Model model, HttpSession session) {
        // Check authentication
        Boolean isAuthenticated = (Boolean) session.getAttribute("isAuthenticated");
        if (isAuthenticated == null || !isAuthenticated) {
            return "redirect:/login";
        }
        
        model.addAttribute("activeIssues", issueService.getAllActiveIssues());
        model.addAttribute("availableBooks", bookService.getAvailableBooks());
        model.addAttribute("issueRequest", new IssueBookRequest());
        model.addAttribute("username", session.getAttribute("username"));
        return "issues";
    }
    
    @GetMapping("/returns")
    public String returnsPage(Model model, HttpSession session) {
        // Check authentication
        Boolean isAuthenticated = (Boolean) session.getAttribute("isAuthenticated");
        if (isAuthenticated == null || !isAuthenticated) {
            return "redirect:/login";
        }
        
        model.addAttribute("activeIssues", issueService.getAllActiveIssues());
        model.addAttribute("overdueIssues", issueService.getOverdueIssues());
        model.addAttribute("returnRequest", new ReturnBookRequest());
        model.addAttribute("username", session.getAttribute("username"));
        return "returns";
    }
    
    @PostMapping("/issues/issue-book")
    public String issueBook(@ModelAttribute IssueBookRequest request, RedirectAttributes redirectAttributes) {
        try {
            issueService.issueBook(request);
            redirectAttributes.addFlashAttribute("successMessage", "Book issued successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error issuing book: " + e.getMessage());
        }
        return "redirect:/issues";
    }
    
    @PostMapping("/returns/return-book")
    public String returnBook(@ModelAttribute ReturnBookRequest request, RedirectAttributes redirectAttributes) {
        try {
            issueService.returnBook(request);
            redirectAttributes.addFlashAttribute("successMessage", "Book returned successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error returning book: " + e.getMessage());
        }
        return "redirect:/returns";
    }
    
    @GetMapping("/students")
    public String studentsPage(Model model, HttpSession session) {
        // Check authentication
        Boolean isAuthenticated = (Boolean) session.getAttribute("isAuthenticated");
        if (isAuthenticated == null || !isAuthenticated) {
            return "redirect:/login";
        }
        
        // Fetch students from student-service
        List<StudentDTO> students = studentServiceClient.getAllStudents();
        model.addAttribute("students", students);
        model.addAttribute("username", session.getAttribute("username"));
        return "students";
    }
}
