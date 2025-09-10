package com.lms.book_service.service;

import com.lms.book_service.dto.LoginRequest;
import com.lms.book_service.dto.AuthRequest;
import com.lms.book_service.dto.StudentDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class StudentServiceClient {
    
    private final WebClient webClient;
    
    public StudentServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }
    
    public List<StudentDTO> getAllStudents() {
        try {
            return webClient.get()
                    .uri("/api/students")
                    .retrieve()
                    .bodyToFlux(StudentDTO.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            System.err.println("Error fetching students: " + e.getMessage());
            return List.of(); // Return empty list if service is down
        }
    }
    
    public StudentDTO getStudentById(Long id) {
        try {
            return webClient.get()
                    .uri("/api/students/id/{id}", id)
                    .retrieve()
                    .bodyToMono(StudentDTO.class)
                    .block();
        } catch (Exception e) {
            System.err.println("Error fetching student by ID: " + e.getMessage());
            return null;
        }
    }
    
    public StudentDTO getStudentByRollNo(String rollNo) {
        try {
            return webClient.get()
                    .uri("/api/students/{rollNo}", rollNo)
                    .retrieve()
                    .bodyToMono(StudentDTO.class)
                    .block();
        } catch (Exception e) {
            System.err.println("Error fetching student by roll number: " + e.getMessage());
            return null;
        }
    }
    
    public String authenticateUser(LoginRequest loginRequest) {
        try {
            // Convert LoginRequest to AuthRequest for the student service
            AuthRequest authRequest = new AuthRequest(loginRequest.getUsername(), loginRequest.getPassword());
            return webClient.post()
                    .uri("/api/auth/login")
                    .bodyValue(authRequest)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            System.err.println("Authentication failed: " + e.getMessage());
            return null;
        }
    }
    
    public boolean validateToken(String token) {
        try {
            String response = webClient.post()
                    .uri("/api/auth/validate?token={token}", token)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return response != null && response.contains("valid");
        } catch (Exception e) {
            return false;
        }
    }
}
