package com.lms.student_service.service;

import com.lms.student_service.entity.Student;
import java.util.List;

public interface StudentService {
	List<Student> getAllStudents();
	Student getStudentById(Long id);
	Student getStudentByRollNo(String rollNo);
	Student addStudent(Student student);
	Student updateStudent(Long id, Student student);
	void deleteStudent(Long id);
}
