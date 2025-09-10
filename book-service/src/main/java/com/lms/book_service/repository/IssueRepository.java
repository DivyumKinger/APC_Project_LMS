package com.lms.book_service.repository;

import com.lms.book_service.entity.Issue;
import com.lms.book_service.entity.Issue.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    
    List<Issue> findByStudentId(Long studentId);
    
    List<Issue> findByBookId(Long bookId);
    
    List<Issue> findByStatus(IssueStatus status);
    
    @Query("SELECT i FROM Issue i WHERE i.book.id = :bookId AND i.studentId = :studentId AND i.status = 'ISSUED'")
    Optional<Issue> findActiveIssueByBookAndStudent(@Param("bookId") Long bookId, @Param("studentId") Long studentId);
    
    @Query("SELECT i FROM Issue i WHERE i.dueDate < :currentDate AND i.status = 'ISSUED'")
    List<Issue> findOverdueIssues(@Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT COUNT(i) FROM Issue i WHERE i.studentId = :studentId AND i.status = 'ISSUED'")
    Long countActiveIssuesByStudent(@Param("studentId") Long studentId);
}
