package com.lms.book_service.service;

import com.lms.book_service.dto.IssueBookRequest;
import com.lms.book_service.dto.ReturnBookRequest;
import com.lms.book_service.entity.Book;
import com.lms.book_service.entity.Issue;
import com.lms.book_service.entity.Issue.IssueStatus;
import com.lms.book_service.repository.BookRepository;
import com.lms.book_service.repository.IssueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class IssueServiceImpl implements IssueService {
    
    private final IssueRepository issueRepository;
    private final BookRepository bookRepository;
    private static final int MAX_BOOKS_PER_STUDENT = 3;
    
    public IssueServiceImpl(IssueRepository issueRepository, BookRepository bookRepository) {
        this.issueRepository = issueRepository;
        this.bookRepository = bookRepository;
    }
    
    @Override
    public Issue issueBook(IssueBookRequest request) {
        // Validate if student can borrow more books
        if (!canStudentBorrowBook(request.getStudentId())) {
            throw new RuntimeException("Student has reached maximum borrowing limit of " + MAX_BOOKS_PER_STUDENT + " books");
        }
        
        // Find the book
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + request.getBookId()));
        
        // Check if book is available
        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("No copies available for book: " + book.getTitle());
        }
        
        // Check if student already has this book issued
        if (issueRepository.findActiveIssueByBookAndStudent(request.getBookId(), request.getStudentId()).isPresent()) {
            throw new RuntimeException("Student already has this book issued");
        }
        
        // Create issue record
        Issue issue = Issue.builder()
                .book(book)
                .studentId(request.getStudentId())
                .studentName(request.getStudentName())
                .remarks(request.getRemarks())
                .build();
        
        // Update book availability
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        if (book.getAvailableCopies() == 0) {
            book.setStatus("BORROWED");
        }
        
        bookRepository.save(book);
        return issueRepository.save(issue);
    }
    
    @Override
    public Issue returnBook(ReturnBookRequest request) {
        Issue issue = issueRepository.findById(request.getIssueId())
                .orElseThrow(() -> new RuntimeException("Issue record not found with id: " + request.getIssueId()));
        
        if (issue.getStatus() != IssueStatus.ISSUED) {
            throw new RuntimeException("Book is already returned or not in issued status");
        }
        
        // Update issue record
        issue.setReturnDate(LocalDateTime.now());
        issue.setStatus(IssueStatus.RETURNED);
        if (request.getRemarks() != null && !request.getRemarks().isEmpty()) {
            issue.setRemarks(issue.getRemarks() + " | Return: " + request.getRemarks());
        }
        
        // Update book availability
        Book book = issue.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        book.setStatus("AVAILABLE");
        
        bookRepository.save(book);
        return issueRepository.save(issue);
    }
    
    @Override
    public List<Issue> getIssuesByStudent(Long studentId) {
        return issueRepository.findByStudentId(studentId);
    }
    
    @Override
    public List<Issue> getIssuesByBook(Long bookId) {
        return issueRepository.findByBookId(bookId);
    }
    
    @Override
    public List<Issue> getAllActiveIssues() {
        return issueRepository.findByStatus(IssueStatus.ISSUED);
    }
    
    @Override
    public List<Issue> getOverdueIssues() {
        List<Issue> overdueIssues = issueRepository.findOverdueIssues(LocalDateTime.now());
        // Update status to OVERDUE
        overdueIssues.forEach(issue -> {
            if (issue.getStatus() == IssueStatus.ISSUED) {
                issue.setStatus(IssueStatus.OVERDUE);
            }
        });
        return issueRepository.saveAll(overdueIssues);
    }
    
    @Override
    public Issue getIssueById(Long issueId) {
        return issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found with id: " + issueId));
    }
    
    @Override
    public boolean canStudentBorrowBook(Long studentId) {
        Long activeIssues = issueRepository.countActiveIssuesByStudent(studentId);
        return activeIssues < MAX_BOOKS_PER_STUDENT;
    }
}
