package com.lms.student_service.entity;

import jakarta.persistence.*;

@Entity
public class UserCredential {
    @Id
    private String username;

    @Column(nullable=false)
    private String password; // store BCrypt hash

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
