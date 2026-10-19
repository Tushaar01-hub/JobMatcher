package com.jobMatcher.project.repository;

import com.jobMatcher.project.entity.User;
import com.jobMatcher.project.enums.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByProviderAndProviderSubject(
            AuthProvider provider,
            String providerSubject
    );
}
