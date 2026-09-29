package com.jobMatcher.project.repository;

import com.jobMatcher.project.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill,Long> {
//    boolean existsBySkill(Skill skill);
    boolean existsByName(String name);
}
