package com.lms.student_service.controller;

import com.lms.student_service.entity.Student;
import com.lms.student_service.service.StudentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
	
	private final StudentService service;
	
	public StudentController(StudentService service) {
		this.service = service;
	}
	
	// Get all students
	@GetMapping
	public List<Student> getAll() {
		return service.getAllStudents();
	}
	
	// Find by roll number
	@GetMapping("/{rollNo}")
	public Student getByRollNo(@PathVariable String rollNo) {
		return service.getStudentByRollNo(rollNo);
	}
	
	@GetMapping("/id/{id}")
	public Student getById(@PathVariable Long id) {
		return service.getStudentById(id);
	}
	
	// Add a student
	@PostMapping
	public Student add(@RequestBody Student student) {
		return service.addStudent(student);
	}
	
	// Update a student
	@PutMapping("/{id}")
	public Student update(@PathVariable Long id, @RequestBody Student student) {
		return service.updateStudent(id, student);
	}
	
	// Delete a student
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.deleteStudent(id);
	}
}