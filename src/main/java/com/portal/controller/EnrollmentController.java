// src/main/java/com/portal/controller/EnrollmentController.java
package com.portal.controller;

import com.portal.model.Enrollment;
import com.portal.model.User;
import com.portal.repository.EnrollmentRepository;
import com.portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "*")
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/my-courses")
    public ResponseEntity<List<Enrollment>> getMyCourses(Authentication auth) {
        String email = auth.getName();
        User user = userRepository.findByEmail(email)
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        List<Enrollment> enrollments = enrollmentRepository.findByUserOrderByEnrolledAtDesc(user);
        return ResponseEntity.ok(enrollments);
    }
}
