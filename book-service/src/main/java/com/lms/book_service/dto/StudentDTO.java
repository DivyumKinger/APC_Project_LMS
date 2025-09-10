package com.lms.book_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private Long id;
    private String name;
    private String rollNo;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
