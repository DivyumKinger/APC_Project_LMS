package com.lms.book_service.controller;

import com.lms.book_service.dto.IssueBookRequest;
import com.lms.book_service.dto.ReturnBookRequest;
import com.lms.book_service.entity.Issue;
import com.lms.book_service.service.IssueService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {
    
    private final IssueService issueService;
    
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }
    
    @PostMapping("/issue")
    public Issue issueBook(@RequestBody IssueBookRequest request) {
        return issueService.issueBook(request);
    }
    
    @PostMapping("/return")
    public Issue returnBook(@RequestBody ReturnBookRequest request) {
        return issueService.returnBook(request);
    }
    
    @GetMapping("/student/{studentId}")
    public List<Issue> getIssuesByStudent(@PathVariable Long studentId) {
        return issueService.getIssuesByStudent(studentId);
    }
    
    @GetMapping("/book/{bookId}")
    public List<Issue> getIssuesByBook(@PathVariable Long bookId) {
        return issueService.getIssuesByBook(bookId);
    }
    
    @GetMapping("/active")
    public List<Issue> getAllActiveIssues() {
        return issueService.getAllActiveIssues();
    }
    
    @GetMapping("/overdue")
    public List<Issue> getOverdueIssues() {
        return issueService.getOverdueIssues();
    }
    
    @GetMapping("/{issueId}")
    public Issue getIssueById(@PathVariable Long issueId) {
        return issueService.getIssueById(issueId);
    }
    
    @GetMapping("/student/{studentId}/can-borrow")
    public boolean canStudentBorrowBook(@PathVariable Long studentId) {
        return issueService.canStudentBorrowBook(studentId);
    }
}
