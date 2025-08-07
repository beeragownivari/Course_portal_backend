package com.portal.service;

import com.portal.model.User;
import com.portal.repository.UserRepository;
import com.portal.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    public Map<String, Object> register(User user) {
        Map<String, Object> response = new HashMap<>();
        
        if (userRepository.existsByEmail(user.getEmail())) {
            response.put("success", false);
            response.put("message", "Email already exists");
            return response;
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        
        String token = jwtUtil.generateToken(savedUser);
        
        response.put("success", true);
        response.put("token", token);
        response.put("user", createUserResponse(savedUser));
        
        return response;
    }
    
    public Map<String, Object> login(String email, String password) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            
            UserDetails userDetails = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            String token = jwtUtil.generateToken(userDetails);
            
            response.put("success", true);
            response.put("token", token);
            response.put("user", createUserResponse((User) userDetails));
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Invalid email or password");
        }
        
        return response;
    }
    
    private Map<String, Object> createUserResponse(User user) {
        Map<String, Object> userResponse = new HashMap<>();
        userResponse.put("id", user.getId());
        userResponse.put("name", user.getName());
        userResponse.put("email", user.getEmail());
        userResponse.put("phone", user.getPhone());
        userResponse.put("role", user.getRole());
        userResponse.put("active", user.isActive());
        return userResponse;
    }
} 