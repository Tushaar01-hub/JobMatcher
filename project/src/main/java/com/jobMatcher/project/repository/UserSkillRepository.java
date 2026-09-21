package com.jobMatcher.project.repository;

import com.jobMatcher.project.entity.Job;
import com.jobMatcher.project.entity.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSkillRepository extends JpaRepository<UserSkill,Long> {
}
