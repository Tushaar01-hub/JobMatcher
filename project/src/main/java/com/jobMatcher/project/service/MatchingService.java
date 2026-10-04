package com.jobMatcher.project.service;

import com.jobMatcher.project.dtos.JobResponseMatchDTO;
import com.jobMatcher.project.entity.Job;
import com.jobMatcher.project.entity.JobSkill;
import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.entity.User;
import com.jobMatcher.project.exception.ResourceNotFoundException;
import com.jobMatcher.project.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MatchingService {
    private final SkillRepository skillRepository;
    JobRepository jobRepository;
    JobSkillRepository jobSkillRepository;
    UserSkillRepository userSkillRepository;
    UserRepository userRepository;

    public MatchingService(JobRepository jobRepository, JobSkillRepository jobSkillRepository, UserSkillRepository userSkillRepository, UserRepository userRepository, SkillRepository skillRepository) {
        this.jobRepository = jobRepository;
        this.jobSkillRepository = jobSkillRepository;
        this.userSkillRepository = userSkillRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }

    public List<JobResponseMatchDTO> getUserMatchingJob(long userId){
        // 1. Get user
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found"));

        // 2. Get user's skills
        List<Long> skillIdList=userSkillRepository.findAllByUserId(userId).stream()
                .map(userSkill -> userSkill.getSkill().getId()).toList();
        // 3. Get all jobs
        Set<Long> userSkillSet = new HashSet<>(skillIdList);

        List<Job> jobList=jobRepository.findAll();

        List<JobResponseMatchDTO> matches = new ArrayList<>();

        for (Job job : jobList) {

            // get this job's skills
            List<JobSkill> jobSkillList=jobSkillRepository.findAllByJobId(job.getId());

            // compare with user's skills
            List<String> matchedSkills=new ArrayList<>();
            List<String> missingSkills=new ArrayList<>();
            for (JobSkill jobSkill:jobSkillList){
//                String skill=skillRepository.findById(jobSkillId).orElseThrow(()->new ResourceNotFoundException("Not Found"));
                if(userSkillSet.contains(jobSkill.getSkill().getId())){
                    matchedSkills.add(jobSkill.getSkill().getName());
                }
                else{
                    missingSkills.add(jobSkill.getSkill().getName());
                }
            }

            // calculate match percentage
            double matchPercentage = 0;

            if (!jobSkillList.isEmpty()) {
                matchPercentage = ((double) matchedSkills.size() / jobSkillList.size()) * 100;
                matchPercentage = Math.round(matchPercentage * 100.0) / 100.0;
            }

            JobResponseMatchDTO dto = new JobResponseMatchDTO();

            dto.setJobId(job.getId());
            dto.setTitle(job.getTitle());
            dto.setCompany(job.getCompany());
            dto.setMatchPercentage(matchPercentage);
            dto.setMatchedSkills(matchedSkills);
            dto.setMissingSkills(missingSkills);

            matches.add(dto);
        }

        return matches;

    }






}
