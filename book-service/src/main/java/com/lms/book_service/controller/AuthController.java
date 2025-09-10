package com.lms.book_service.controller;

import com.lms.book_service.dto.LoginRequest;
import com.lms.book_service.service.StudentServiceClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    
    private final StudentServiceClient studentServiceClient;
    
    public AuthController(StudentServiceClient studentServiceClient) {
        this.studentServiceClient = studentServiceClient;
    }
    
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        try {
            // First try admin fallback authentication
            if (authenticateAdmin(loginRequest)) {
                session.setAttribute("jwtToken", "admin-token");
                session.setAttribute("username", loginRequest.getUsername());
                session.setAttribute("isAuthenticated", true);
                session.setAttribute("userRole", "ADMIN");
                redirectAttributes.addFlashAttribute("successMessage", "Login successful!");
                return "redirect:/dashboard";
            }
            
            // If not admin, try student service authentication
            String token = studentServiceClient.authenticateUser(loginRequest);
            if (token != null && !token.isEmpty()) {
                session.setAttribute("jwtToken", token);
                session.setAttribute("username", loginRequest.getUsername());
                session.setAttribute("isAuthenticated", true);
                session.setAttribute("userRole", "USER");
                redirectAttributes.addFlashAttribute("successMessage", "Login successful!");
                return "redirect:/dashboard";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password");
                return "redirect:/login?error";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Login failed: " + e.getMessage());
            return "redirect:/login?error";
        }
    }
    
    private boolean authenticateAdmin(LoginRequest loginRequest) {
        // Admin fallback authentication
        return "admin".equals(loginRequest.getUsername()) && "admin123".equals(loginRequest.getPassword());
    }
    
    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("successMessage", "Successfully logged out!");
        return "redirect:/login";
    }
    
    @GetMapping("/")
    public String home(HttpSession session) {
        Boolean isAuthenticated = (Boolean) session.getAttribute("isAuthenticated");
        if (isAuthenticated != null && isAuthenticated) {
            return "redirect:/dashboard";
        }
        return "redirect:/login";
    }
}
