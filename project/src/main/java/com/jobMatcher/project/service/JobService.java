package com.jobMatcher.project.service;

import com.jobMatcher.project.dtos.JobRequestDTO;
import com.jobMatcher.project.dtos.JobResponseDTO;
import com.jobMatcher.project.dtos.JobUpdateRequestDTO;
import com.jobMatcher.project.dtos.JobUpdatedResponseDTO;
import com.jobMatcher.project.exception.ResourceNotFoundException;
import com.jobMatcher.project.entity.Job;
import com.jobMatcher.project.entity.JobSkill;
import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.repository.JobRepository;
import com.jobMatcher.project.repository.JobSkillRepository;
import com.jobMatcher.project.repository.SkillRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {
    JobRepository jobRepository;
    private final ModelMapper modelMapper;
    JobSkillRepository jobSkillRepository;
    SkillRepository skillRepository;

    public JobService(ModelMapper modelMapper, JobRepository jobRepository, JobSkillRepository jobSkillRepository, SkillRepository skillRepository) {
        this.modelMapper = modelMapper;
        this.jobRepository = jobRepository;
        this.jobSkillRepository = jobSkillRepository;
        this.skillRepository = skillRepository;
    }

    public JobResponseDTO createJob(JobRequestDTO jobRequestDTO){
        Job job=modelMapper.map(jobRequestDTO, Job.class);
//        if(jobRepository.existsByJob(job)){
//            throw new DuplicateResourceException("Job Already Exists");
//        }
        List<Skill> skillList=jobRequestDTO.getSkillIds().stream().map(skillId->skillRepository.findById(skillId).orElseThrow(()->new ResourceNotFoundException("Skill Not Found"))).toList();
        Job savedJob=jobRepository.save(job);
//        long savedJobId=savedJob.getId();
        for(Skill skill:skillList){
//            Skill skill = skillRepository.findById(skillId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Skill Not Found"));
            JobSkill jobSkill=new JobSkill();
            jobSkill.setSkill(skill);
            jobSkill.setJob(savedJob);
            jobSkillRepository.save(jobSkill);

        }
        JobResponseDTO jobResponseDTO=modelMapper.map(savedJob,JobResponseDTO.class);
        List<String> jobSkills=jobSkillRepository.findAllByJobId(savedJob.getId())
                .stream().map(jobSkill -> jobSkill.getSkill().getName()).toList();
        jobResponseDTO.setSkills(jobSkills);
        return jobResponseDTO;
    }

    public List<JobResponseDTO> getAllJobs(){

        List<Job> jobs=jobRepository.findAll();
        List<JobResponseDTO> jobResponseDTOList=new ArrayList<>();
        for (int i = 0; i < jobs.size(); i++) {
            jobResponseDTOList.add(modelMapper.map((jobs.get(i)),JobResponseDTO.class));
        }
        for (int i = 0; i < jobResponseDTOList.size(); i++) {
            List<String> jobSkills=jobSkillRepository.findAllByJobId(jobs.get(i).getId())
                    .stream().map(jobSkill -> jobSkill.getSkill().getName()).toList();
            jobResponseDTOList.get(i).setSkills(jobSkills);
        }
        return jobResponseDTOList;

    }

    public JobResponseDTO getJob(long id){
        Job job=jobRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Job Not Found") );
        JobResponseDTO jobResponseDTO=modelMapper.map(job,JobResponseDTO.class);
        jobResponseDTO.setSkills(jobSkillRepository.findAllByJobId(id).stream().
                map(jobSkill -> jobSkill.getSkill().getName()).toList());
        return jobResponseDTO;

    }

    public JobUpdatedResponseDTO updateJob(long id, JobUpdateRequestDTO jobUpdateRequestDTO){
        Job job=jobRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Job Not Found") );
        job.setExperienceLevel(jobUpdateRequestDTO.getExperienceLevel());
        job.setSalary(jobUpdateRequestDTO.getSalary());
        job.setTitle(jobUpdateRequestDTO.getTitle());
        job.setCompany(jobUpdateRequestDTO.getCompany());
        job.setDescription(jobUpdateRequestDTO.getDescription());
        Job updatedJob=jobRepository.save(job);
        JobUpdatedResponseDTO jobUpdatedResponseDTO=modelMapper.map(updatedJob,JobUpdatedResponseDTO.class);
        jobUpdatedResponseDTO.setSkills(jobSkillRepository.findAllByJobId(updatedJob.getId()).stream().map(jobSkill -> jobSkill.getSkill().getName()).toList());
        return jobUpdatedResponseDTO;

    }
    public void deleteJob(long id){
        Job job=jobRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Job Not Found") );

        List<JobSkill> jobSkillList=jobSkillRepository.findAllByJobId(id);
        jobSkillRepository.deleteAll(jobSkillList);
        jobRepository.delete(job);


    }
}
