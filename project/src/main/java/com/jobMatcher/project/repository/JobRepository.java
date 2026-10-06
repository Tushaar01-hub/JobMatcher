package com.jobMatcher.project.repository;

import com.jobMatcher.project.entity.Job;
import com.jobMatcher.project.entity.JobSkill;
import com.jobMatcher.project.enums.ExperienceLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface JobRepository extends JpaRepository<Job,Long>, JpaSpecificationExecutor<Job> {
    List<Job> findByTitleContainingIgnoreCase(String title);
    List<Job> findByExperienceLevel(ExperienceLevel explvl);

    List<Job> findBySalaryGreaterThanEqual(long salaryIsGreaterThan);
    List<Job> findByCompanyContainingIgnoreCase(String company);


}
