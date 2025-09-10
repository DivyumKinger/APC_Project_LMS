package com.lms.student_service.controller;

import com.lms.student_service.entity.UserCredential;
import com.lms.student_service.repository.UserCredentialRepository;
import com.lms.student_service.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserCredentialRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserCredentialRepository repository,
                         PasswordEncoder passwordEncoder,
                         JwtUtil jwtUtil,
                         AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserCredential user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authenticate.isAuthenticated()) {
            return ResponseEntity.ok(jwtUtil.generateToken(authRequest.getUsername()));
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
        if (jwtUtil.isTokenValid(token)) {
            return ResponseEntity.ok("Token is valid");
        } else {
            throw new RuntimeException("Invalid token");
        }
    }

    public static class AuthRequest {
        private String username;
        private String password;
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
