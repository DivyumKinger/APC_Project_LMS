package com.lms.book_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueBookRequest {
    private Long bookId;
    private Long studentId;
    private String studentName;
    private String remarks;
}
