package com.jobMatcher.project.service;

import com.jobMatcher.project.entity.Job;
import com.jobMatcher.project.entity.JobSkill;
import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.repository.JobRepository;
import com.jobMatcher.project.repository.JobSkillRepository;
import com.jobMatcher.project.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSkillService {
    JobSkillRepository jobSkillRepository;
    JobRepository jobRepository;
    SkillRepository skillRepository;

    public JobSkillService(JobSkillRepository jobSkillRepository, JobRepository jobRepository, SkillRepository skillRepository) {
        this.jobSkillRepository = jobSkillRepository;
        this.jobRepository = jobRepository;
        this.skillRepository = skillRepository;
    }
    //    1. addSkillToJob()
    public JobSkill addSkillToJob(long jobId,long skillId){
        if(jobSkillRepository.existsByJobIdAndSkillId(jobId,skillId)){
            throw new RuntimeException("Job with that skill already exists");
        }
        Job job=jobRepository.findById(jobId).orElseThrow();
        Skill skill=skillRepository.findById(skillId).orElseThrow();
        JobSkill jobSkill=new JobSkill();
        jobSkill.setJob(job);
        jobSkill.setSkill(skill);
        jobSkillRepository.save(jobSkill);
        return jobSkill;
    }
//2. getJobSkills()
    public List<JobSkill> getJobSkills(long jobId){
        return jobSkillRepository.findAllByJobId(jobId);
    }
//3. removeSkillFromJob()
    public void removeSkillFromJob(long jobId,long skillId){
        JobSkill jobSkill=jobSkillRepository.findByJobIdAndSkillId(jobId,skillId);
        if (jobSkill == null) {
            throw new RuntimeException("Job does not have this skill");
        }
        jobSkillRepository.delete(jobSkill);
    }
}
