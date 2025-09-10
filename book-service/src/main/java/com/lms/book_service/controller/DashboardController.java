package com.lms.book_service.controller;

import com.lms.book_service.entity.Issue;
import com.lms.book_service.service.BookService;
import com.lms.book_service.service.IssueService;
import com.lms.book_service.service.StudentServiceClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DashboardController {
    
    private final BookService bookService;
    private final IssueService issueService;
    private final StudentServiceClient studentServiceClient;
    
    public DashboardController(BookService bookService, IssueService issueService, StudentServiceClient studentServiceClient) {
        this.bookService = bookService;
        this.issueService = issueService;
        this.studentServiceClient = studentServiceClient;
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        // Check authentication
        Boolean isAuthenticated = (Boolean) session.getAttribute("isAuthenticated");
        if (isAuthenticated == null || !isAuthenticated) {
            return "redirect:/login";
        }
        
        // Get dashboard statistics
        long totalBooks = bookService.getAllBooks().size();
        long totalStudents = getTotalStudentsCount();
        long totalIssues = issueService.getAllActiveIssues().size();
        long overdueBooks = issueService.getOverdueIssues().size();
        
        // Get recent issues for the table
        List<Issue> recentIssues = getRecentIssues();
        
        // Add data to model
        model.addAttribute("totalBooks", totalBooks);
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("totalIssues", totalIssues);
        model.addAttribute("overdueBooks", overdueBooks);
        model.addAttribute("recentIssues", recentIssues);
        model.addAttribute("username", session.getAttribute("username"));
        
        return "dashboard";
    }
    
    private long getTotalStudentsCount() {
        try {
            return studentServiceClient.getAllStudents().size();
        } catch (Exception e) {
            return 0;
        }
    }
    
    private List<Issue> getRecentIssues() {
        // Get last 5 active issues
        List<Issue> allIssues = issueService.getAllActiveIssues();
        return allIssues.stream()
                .sorted((i1, i2) -> i2.getIssueDate().compareTo(i1.getIssueDate()))
                .limit(5)
                .toList();
    }
}
