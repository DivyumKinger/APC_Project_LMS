package com.lms.student_service.service;

import com.lms.student_service.entity.Student;
import com.lms.student_service.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
	
	private final StudentRepository repo;
	
	public StudentServiceImpl(StudentRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public List<Student> getAllStudents() {
		return repo.findAll();
	}
	
	@Override
	public Student getStudentById(Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
	}
	
	@Override
	public Student getStudentByRollNo(String rollNo) {
		return repo.findByRollNo(rollNo)
				.orElseThrow(() -> new RuntimeException("Student not found with roll number: " + rollNo));
	}
	
	@Override
	public Student addStudent(Student student) {
		return repo.save(student);
	}
	
	@Override
	public Student updateStudent(Long id, Student student) {
		Student existingStudent = getStudentById(id);
		existingStudent.setName(student.getName());
		existingStudent.setEmail(student.getEmail());
		existingStudent.setRollNo(student.getRollNo());
		return repo.save(existingStudent);
	}
	
	@Override
	public void deleteStudent(Long id) {
		if (!repo.existsById(id)) {
			throw new RuntimeException("Student not found with id: " + id);
		}
		repo.deleteById(id);
	}
}