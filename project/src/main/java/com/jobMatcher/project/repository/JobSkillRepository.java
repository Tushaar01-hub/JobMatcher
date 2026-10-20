package com.jobMatcher.project.repository;

import com.jobMatcher.project.entity.Job;
import com.jobMatcher.project.entity.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobSkillRepository extends JpaRepository<JobSkill,Long> {
    boolean existsByJobIdAndSkillId(long jobId,long skillId);
    List<JobSkill> findAllByJobId(long jobId);
    JobSkill findByJobIdAndSkillId(long jobId,long skillId);
    List<JobSkill> findBySkill_NameContainingIgnoreCase(String skillName);
//    List<JobSkill> findAllByJobIdIn(List<Long> jobIds);
@Query("""
       SELECT js
       FROM JobSkill js
       JOIN FETCH js.skill
       JOIN FETCH js.job
       WHERE js.job.id IN :jobIds
       """)
List<JobSkill> findAllByJobIdIn(@Param("jobIds") List<Long> jobIds);
    boolean existsBySkillId(long skillId);
}
