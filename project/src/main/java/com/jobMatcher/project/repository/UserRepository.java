package com.jobMatcher.project.repository;

import com.jobMatcher.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
}
