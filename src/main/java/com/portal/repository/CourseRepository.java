package com.portal.repository;

import com.portal.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    Page<Course> findByActive(boolean active, Pageable pageable);
    
    Page<Course> findByCategory(String category, Pageable pageable);
    
    Page<Course> findByDifficulty(Course.Difficulty difficulty, Pageable pageable);
    
    Page<Course> findByInstructorContainingIgnoreCase(String instructor, Pageable pageable);
    
    @Query("SELECT c FROM Course c WHERE " +
           "(:category IS NULL OR c.category = :category) AND " +
           "(:difficulty IS NULL OR c.difficulty = :difficulty) AND " +
           "(:search IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.instructor) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
           "c.active = true")
    Page<Course> findByFilters(
            @Param("category") String category,
            @Param("difficulty") Course.Difficulty difficulty,
            @Param("search") String search,
            Pageable pageable
    );
    
    @Query("SELECT DISTINCT c.category FROM Course c WHERE c.active = true")
    List<String> findAllCategories();
    
    @Query("SELECT COUNT(c) FROM Course c WHERE c.active = true")
    Long countActiveCourses();
    
    @Query("SELECT c FROM Course c WHERE c.active = true ORDER BY c.enrolledStudents DESC LIMIT :limit")
    List<Course> findFeaturedCourses(@Param("limit") int limit);
} 