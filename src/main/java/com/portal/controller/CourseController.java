package com.portal.controller;

import com.portal.model.Course;
import com.portal.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {
    
    @Autowired
    private CourseRepository courseRepository;
    
    @GetMapping
    public ResponseEntity<?> getCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) Boolean featured,
            @RequestParam(required = false) Integer limit) {
        
        if (featured != null && featured) {
            List<Course> featuredCourses = courseRepository.findFeaturedCourses(limit != null ? limit : 6);
            return ResponseEntity.ok(featuredCourses);
        }
        
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Course.Difficulty difficultyEnum = null;
        if (difficulty != null && !difficulty.isEmpty()) {
            try {
                difficultyEnum = Course.Difficulty.valueOf(difficulty.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Invalid difficulty, will be ignored
            }
        }
        
        Page<Course> courses;
        if (category != null || difficultyEnum != null || search != null) {
            courses = courseRepository.findByFilters(category, difficultyEnum, search, pageable);
        } else {
            courses = courseRepository.findByActive(true, pageable);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("content", courses.getContent());
        response.put("totalPages", courses.getTotalPages());
        response.put("totalElements", courses.getTotalElements());
        response.put("currentPage", courses.getNumber());
        response.put("size", courses.getSize());
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getCourse(@PathVariable Long id) {
        Optional<Course> course = courseRepository.findById(id);
        
        if (course.isPresent()) {
            return ResponseEntity.ok(course.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = courseRepository.findAllCategories();
        return ResponseEntity.ok(categories);
    }
} 