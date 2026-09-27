package com.jobMatcher.project.service;

import com.jobMatcher.project.dtos.JobSkillResponseDTO;
import com.jobMatcher.project.entity.Job;
import com.jobMatcher.project.entity.JobSkill;
import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.repository.JobRepository;
import com.jobMatcher.project.repository.JobSkillRepository;
import com.jobMatcher.project.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public JobSkillResponseDTO addSkillToJob(long jobId,long skillId){
        if(jobSkillRepository.existsByJobIdAndSkillId(jobId,skillId)){
            throw new RuntimeException("Job with that skill already exists");
        }
        Job job=jobRepository.findById(jobId).orElseThrow();
        Skill skill=skillRepository.findById(skillId).orElseThrow();
        JobSkill jobSkill=new JobSkill();
        jobSkill.setJob(job);
        jobSkill.setSkill(skill);
        jobSkillRepository.save(jobSkill);
        JobSkillResponseDTO jobSkillResponseDTO=new JobSkillResponseDTO();
        jobSkillResponseDTO.setSkillName(skill.getName());
        jobSkillResponseDTO.setSkillId(skillId);
        return jobSkillResponseDTO;
    }
//2. getJobSkills()
    public List<JobSkillResponseDTO> getJobSkills(long jobId){
        List<JobSkill> jobSkillList=jobSkillRepository.findAllByJobId(jobId);
        List<JobSkillResponseDTO> jobSkillResponseDTOList=new ArrayList<>();
        for(JobSkill jobSkill:jobSkillList){
            JobSkillResponseDTO jobSkillResponseDTO=new JobSkillResponseDTO();
            jobSkillResponseDTO.setSkillName(jobSkillResponseDTO.getSkillName());
            jobSkillResponseDTO.setSkillId(jobSkill.getSkill().getId());
            jobSkillResponseDTOList.add(jobSkillResponseDTO);
        }
        return jobSkillResponseDTOList;
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
