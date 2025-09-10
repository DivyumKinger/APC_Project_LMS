package com.lms.book_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnBookRequest {
    private Long issueId;
    private String remarks;
}
