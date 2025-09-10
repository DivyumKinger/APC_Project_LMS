package com.lms.book_service.service;

import com.lms.book_service.dto.IssueBookRequest;
import com.lms.book_service.dto.ReturnBookRequest;
import com.lms.book_service.entity.Issue;
import java.util.List;

public interface IssueService {
    
    Issue issueBook(IssueBookRequest request);
    
    Issue returnBook(ReturnBookRequest request);
    
    List<Issue> getIssuesByStudent(Long studentId);
    
    List<Issue> getIssuesByBook(Long bookId);
    
    List<Issue> getAllActiveIssues();
    
    List<Issue> getOverdueIssues();
    
    Issue getIssueById(Long issueId);
    
    boolean canStudentBorrowBook(Long studentId);
}
