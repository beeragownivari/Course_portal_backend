package com.portal.repository;

import com.portal.model.Enrollment;
import com.portal.model.User;
import com.portal.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByUser(User user);

    List<Enrollment> findByUserOrderByEnrolledAtDesc(User user);

    Optional<Enrollment> findByUserAndCourse(User user, Course course);

    boolean existsByUserAndCourse(User user, Course course);

    @Query("SELECT e FROM Enrollment e WHERE e.user = :user AND e.status = 'COMPLETED'")
    List<Enrollment> findCompletedEnrollmentsByUser(@Param("user") User user);

    @Query("SELECT e FROM Enrollment e WHERE e.user = :user AND e.status IN ('ENROLLED', 'IN_PROGRESS')")
    List<Enrollment> findActiveEnrollmentsByUser(@Param("user") User user);

    @Query("SELECT AVG(e.progressPercentage) FROM Enrollment e WHERE e.user = :user")
    Double getAverageProgressByUser(@Param("user") User user);

    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.status = 'COMPLETED'")
    Long countCompletedEnrollments();

    // ── Count enrollments in the past 30 days ───────────────────────────────
    @Query(value = """
        SELECT COUNT(*) 
          FROM enrollments 
         WHERE enrolled_at >= CURDATE() - INTERVAL 30 DAY
        """,
        nativeQuery = true)
    Long countEnrollmentsThisMonth();

    // ── Fetch N most-recent enrollments ────────────────────────────────────
    @Query(value = """
        SELECT * 
          FROM enrollments 
         ORDER BY enrolled_at DESC 
         LIMIT :limit
        """,
        nativeQuery = true)
    List<Enrollment> findRecentEnrollments(@Param("limit") int limit);

    // ── Total revenue from completed payments ───────────────────────────────
    @Query("SELECT SUM(e.amount) FROM Enrollment e WHERE e.paymentStatus = 'COMPLETED'")
    Double getTotalRevenue();

    // ── Revenue in the past 30 days ────────────────────────────────────────
    @Query(value = """
        SELECT SUM(amount) 
          FROM enrollments 
         WHERE payment_status = 'COMPLETED' 
           AND enrolled_at >= CURDATE() - INTERVAL 30 DAY
        """,
        nativeQuery = true)
    Double getMonthlyRevenue();
}
