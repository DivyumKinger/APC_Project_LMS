package com.lms.student_service.entity;

import jakarta.persistence.*;
		import lombok.*;
		
		import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String rollNo;
	private String email;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	// --- Hibernate Lifecycle Hooks ---
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
	}
	
	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
	
	@PreRemove
	public void preRemove() {
		System.out.println("Student with ID " + id + " is being deleted.");
	}
}