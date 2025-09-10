package com.lms.book_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
    @Column(nullable = false)
    private Long studentId; // Reference to student from student-service
    
    @Column(nullable = false)
    private String studentName;
    
    @Column(nullable = false)
    private LocalDateTime issueDate;
    
    @Column(nullable = false)
    private LocalDateTime dueDate;
    
    private LocalDateTime returnDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueStatus status;
    
    private String remarks;
    
    @PrePersist
    public void prePersist() {
        this.issueDate = LocalDateTime.now();
        this.dueDate = this.issueDate.plusDays(14); // 14 days borrowing period
        this.status = IssueStatus.ISSUED;
    }
    
    @PreUpdate
    public void preUpdate() {
        if (this.returnDate != null && this.status == IssueStatus.ISSUED) {
            this.status = IssueStatus.RETURNED;
        }
    }
    
    public enum IssueStatus {
        ISSUED,
        RETURNED,
        OVERDUE,
        LOST
    }
}
