package com.portal.repository;

import com.portal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByRole(User.Role role);

    List<User> findByActive(boolean active);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'STUDENT'")
    Long countStudents();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'ADMIN'")
    Long countAdmins();

    // ✅ Native MySQL query to count users created in the last 7 days
    @Query(value = "SELECT COUNT(*) FROM users WHERE created_at >= CURDATE() - INTERVAL 7 DAY", nativeQuery = true)
    Long countNewUsersThisWeek();
}
