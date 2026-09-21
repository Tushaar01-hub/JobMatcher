package com.jobMatcher.project.repository;

import com.jobMatcher.project.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job,Long> {
}
