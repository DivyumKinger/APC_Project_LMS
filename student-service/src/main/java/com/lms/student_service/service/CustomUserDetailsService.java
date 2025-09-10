package com.lms.student_service.service;

import com.lms.student_service.entity.UserCredential;
import com.lms.student_service.repository.UserCredentialRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.*;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserCredentialRepository repository;

    public CustomUserDetailsService(UserCredentialRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential user = repository.findById(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }
}
