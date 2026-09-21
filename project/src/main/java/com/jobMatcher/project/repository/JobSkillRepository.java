package com.jobMatcher.project.repository;

import com.jobMatcher.project.entity.Job;
import com.jobMatcher.project.entity.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSkillRepository extends JpaRepository<JobSkill,Long> {
}
