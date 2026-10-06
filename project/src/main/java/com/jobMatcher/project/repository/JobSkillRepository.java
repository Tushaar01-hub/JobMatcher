package com.jobMatcher.project.repository;

import com.jobMatcher.project.entity.Job;
import com.jobMatcher.project.entity.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSkillRepository extends JpaRepository<JobSkill,Long> {
    boolean existsByJobIdAndSkillId(long jobId,long skillId);
    List<JobSkill> findAllByJobId(long jobId);
    JobSkill findByJobIdAndSkillId(long jobId,long skillId);
    List<JobSkill> findBySkill_NameContainingIgnoreCase(String skillName);

}
