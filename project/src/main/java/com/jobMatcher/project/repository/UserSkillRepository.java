package com.jobMatcher.project.repository;

import com.jobMatcher.project.entity.Job;
import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.entity.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSkillRepository extends JpaRepository<UserSkill,Long> {
    boolean existsByUserIdAndSkillId(long userId,long skillId);

    List<UserSkill> findAllByUserId(long userId);
    UserSkill findByUserIdAndSkillId(long userId,long skillId);
}
