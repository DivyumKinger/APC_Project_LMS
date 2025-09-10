package com.lms.student_service.service;

import com.lms.student_service.entity.Student;
import com.lms.student_service.entity.UserCredential;
import com.lms.student_service.repository.StudentRepository;
import com.lms.student_service.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class StudentDataInitializationService implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private UserCredentialRepository userCredentialRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("Starting student data initialization...");
        
        // Clear existing data
        clearExistingData();
        
        // Add fresh sample data
        addSampleStudents();
        addSampleUserCredentials();
        
        System.out.println("Student data initialization completed successfully!");
    }

    private void clearExistingData() {
        System.out.println("Clearing existing student data...");
        
        // Delete all user credentials
        userCredentialRepository.deleteAll();
        System.out.println("Cleared all user credentials");
        
        // Delete all students
        studentRepository.deleteAll();
        System.out.println("Cleared all students");
    }

    private void addSampleStudents() {
        System.out.println("Adding sample students...");
        
        List<Student> sampleStudents = Arrays.asList(
            createStudent("John Smith", "CS001", "john.smith@university.edu"),
            createStudent("Emily Johnson", "CS002", "emily.johnson@university.edu"),
            createStudent("Michael Brown", "CS003", "michael.brown@university.edu"),
            createStudent("Sarah Davis", "CS004", "sarah.davis@university.edu"),
            createStudent("David Wilson", "CS005", "david.wilson@university.edu"),
            createStudent("Jessica Miller", "CS006", "jessica.miller@university.edu"),
            createStudent("Christopher Garcia", "CS007", "christopher.garcia@university.edu"),
            createStudent("Ashley Martinez", "CS008", "ashley.martinez@university.edu"),
            createStudent("Daniel Anderson", "CS009", "daniel.anderson@university.edu"),
            createStudent("Amanda Taylor", "CS010", "amanda.taylor@university.edu"),
            createStudent("Joshua Thomas", "EE001", "joshua.thomas@university.edu"),
            createStudent("Jennifer Jackson", "EE002", "jennifer.jackson@university.edu"),
            createStudent("Matthew White", "EE003", "matthew.white@university.edu"),
            createStudent("Stephanie Harris", "EE004", "stephanie.harris@university.edu"),
            createStudent("Ryan Clark", "EE005", "ryan.clark@university.edu"),
            createStudent("Nicole Lewis", "ME001", "nicole.lewis@university.edu"),
            createStudent("Andrew Robinson", "ME002", "andrew.robinson@university.edu"),
            createStudent("Megan Walker", "ME003", "megan.walker@university.edu"),
            createStudent("Kevin Hall", "ME004", "kevin.hall@university.edu"),
            createStudent("Lauren Allen", "ME005", "lauren.allen@university.edu")
        );

        studentRepository.saveAll(sampleStudents);
        System.out.println("Added " + sampleStudents.size() + " sample students");
    }

    private void addSampleUserCredentials() {
        System.out.println("Adding sample user credentials...");
        
        List<UserCredential> sampleCredentials = Arrays.asList(
            createUserCredential("admin", "admin123"),
            createUserCredential("CS001", "password123"),
            createUserCredential("CS002", "password123"),
            createUserCredential("CS003", "password123"),
            createUserCredential("CS004", "password123"),
            createUserCredential("CS005", "password123"),
            createUserCredential("CS006", "password123"),
            createUserCredential("CS007", "password123"),
            createUserCredential("CS008", "password123"),
            createUserCredential("CS009", "password123"),
            createUserCredential("CS010", "password123"),
            createUserCredential("EE001", "password123"),
            createUserCredential("EE002", "password123"),
            createUserCredential("EE003", "password123"),
            createUserCredential("EE004", "password123"),
            createUserCredential("EE005", "password123"),
            createUserCredential("ME001", "password123"),
            createUserCredential("ME002", "password123"),
            createUserCredential("ME003", "password123"),
            createUserCredential("ME004", "password123"),
            createUserCredential("ME005", "password123")
        );

        userCredentialRepository.saveAll(sampleCredentials);
        System.out.println("Added " + sampleCredentials.size() + " sample user credentials");
    }

    private Student createStudent(String name, String rollNo, String email) {
        Student student = new Student();
        student.setName(name);
        student.setRollNo(rollNo);
        student.setEmail(email);
        student.setCreatedAt(LocalDateTime.now());
        return student;
    }

    private UserCredential createUserCredential(String username, String password) {
        UserCredential credential = new UserCredential();
        credential.setUsername(username);
        credential.setPassword(passwordEncoder.encode(password));
        return credential;
    }
}
