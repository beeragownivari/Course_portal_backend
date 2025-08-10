package com.portal.controller;

import com.portal.model.Course;
import com.portal.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.*;

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
                // Ignore invalid difficulty
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
        return courseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(courseRepository.findAllCategories());
    }

    // ✅ New API to create a course
    @PostMapping
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course) {
        course.setId(null); // ensure ID is generated
        Course savedCourse = courseRepository.save(course);
        return ResponseEntity.ok(savedCourse);
    }
}
