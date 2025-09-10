package com.lms.book_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private String author;
	private int availableCopies;
	private int totalQuantity;
	private boolean isIssued = false; // Add the missing field with default value
	private String status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@ToString.Exclude
	private List<Issue> issues;
	
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
		this.status = "AVAILABLE";
		// Set totalQuantity to availableCopies if not set
		if (this.totalQuantity == 0) {
			this.totalQuantity = this.availableCopies;
		}
		// Set isIssued to false by default
		if (this.availableCopies > 0) {
			this.isIssued = false;
		}
	}
	
	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}