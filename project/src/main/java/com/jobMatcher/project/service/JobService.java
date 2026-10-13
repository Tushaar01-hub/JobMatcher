package com.jobMatcher.project.service;

import com.jobMatcher.project.dtos.JobRequestDTO;
import com.jobMatcher.project.dtos.JobResponseDTO;
import com.jobMatcher.project.dtos.JobUpdateRequestDTO;
import com.jobMatcher.project.dtos.JobUpdatedResponseDTO;
import com.jobMatcher.project.enums.ExperienceLevel;
import com.jobMatcher.project.exception.ResourceNotFoundException;
import com.jobMatcher.project.entity.Job;
import com.jobMatcher.project.entity.JobSkill;
import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.repository.JobRepository;
import com.jobMatcher.project.repository.JobSkillRepository;
import com.jobMatcher.project.repository.SkillRepository;
import com.jobMatcher.project.specification.JobSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<JobResponseDTO> searchJobByTitle(String title){
        List<Job> jobList=jobRepository.findByTitleContainingIgnoreCase(title);
        List<JobResponseDTO> jobResponseDTOList=new ArrayList<>();
        for(Job job:jobList){
            JobResponseDTO jobResponseDTO=modelMapper.map(job,JobResponseDTO.class);
            jobResponseDTO.setSkills(job.getJobSkills().stream().map(jobSkill -> jobSkill.getSkill().getName()).toList());
            jobResponseDTOList.add(jobResponseDTO);

        }
        return jobResponseDTOList;
    }
    public List<JobResponseDTO> searchByExperienceLevel(ExperienceLevel explvl){
        List<Job> jobList=jobRepository.findByExperienceLevel(explvl);
        List<JobResponseDTO> jobResponseDTOList=new ArrayList<>();
        for(Job job:jobList){
            JobResponseDTO jobResponseDTO=modelMapper.map(job,JobResponseDTO.class);
            jobResponseDTO.setSkills(job.getJobSkills().stream().map(jobSkill -> jobSkill.getSkill().getName()).toList());
            jobResponseDTOList.add(jobResponseDTO);
        }
        return jobResponseDTOList;
    }
    public List<JobResponseDTO> searchBySalary(long salary){
        List<Job> jobList=jobRepository.findBySalaryGreaterThanEqual(salary);
        List<JobResponseDTO> jobResponseDTOList=new ArrayList<>();
        for(Job job:jobList){
            JobResponseDTO jobResponseDTO=modelMapper.map(job,JobResponseDTO.class);
            jobResponseDTO.setSkills(job.getJobSkills().stream().map(jobSkill -> jobSkill.getSkill().getName()).toList());
            jobResponseDTOList.add(jobResponseDTO);
        }
        return jobResponseDTOList;
    }
    public List<JobResponseDTO> searchByCompany(String company){
        List<Job> jobList=jobRepository.findByCompanyContainingIgnoreCase(company);
        List<JobResponseDTO> jobResponseDTOList=new ArrayList<>();
        for(Job job:jobList){
            JobResponseDTO jobResponseDTO=modelMapper.map(job,JobResponseDTO.class);
            jobResponseDTO.setSkills(job.getJobSkills().stream().map(jobSkill -> jobSkill.getSkill().getName()).toList());
            jobResponseDTOList.add(jobResponseDTO);
        }
        return jobResponseDTOList;
    }
    public List<JobResponseDTO> searchBySkill(String skillname){
        List<JobSkill> jobSkillList=jobSkillRepository.findBySkill_NameContainingIgnoreCase(skillname);
        List<JobResponseDTO> jobResponseDTOList=new ArrayList<>();
        for(JobSkill jobSkills:jobSkillList){
            Job job=jobSkills.getJob();
            JobResponseDTO jobResponseDTO=modelMapper.map(job,JobResponseDTO.class);
            jobResponseDTO.setSkills(job.getJobSkills().stream().map(jobSkill -> jobSkill.getSkill().getName()).toList());
            jobResponseDTOList.add(jobResponseDTO);
        }
        return jobResponseDTOList;
    }
    public Page<JobResponseDTO> getJobsByFilter(String title, ExperienceLevel explvl, Long salary,
                                                String company, String skillName, Pageable pageable){
        Specification<Job> specification=(root, query, criteriaBuilder) -> null;
        if(title!=null){
           specification= specification.and(JobSpecification.hasTitle(title));
        }
        if(explvl!=null){
            specification=specification.and(JobSpecification.hasExpLvl(explvl));
        }
        if(salary!=null){
            specification=specification.and(JobSpecification.hasMinSalary(salary));
        }
        if(company!=null){
            specification=specification.and(JobSpecification.hasCompany(company));
        }
        if(skillName!=null){
            specification=specification.and(JobSpecification.hasSkill(skillName));
        }
//        List<Job> jobList=jobRepository.findAll(specification);
        Page<Job> jobPage = jobRepository.findAll(specification, pageable);
        List<Long> jobIds = jobPage.getContent()
                .stream()
                .map(Job::getId)
                .toList();
        List<JobSkill> jobSkills =
                jobSkillRepository.findAllByJobIdIn(jobIds);
        Map<Long, List<JobSkill>> skillsByJobId =
                jobSkills.stream()
                        .collect(Collectors.groupingBy(
                                js -> js.getJob().getId()
                        ));
        Page<JobResponseDTO> jobResponseDTOPage = jobPage.map(job -> {

//            JobResponseDTO dto = modelMapper.map(job, JobResponseDTO.class);

            JobResponseDTO dto=new JobResponseDTO();
            dto.setTitle(job.getTitle());
            dto.setDescription(job.getDescription());
            dto.setSalary(job.getSalary());
            dto.setCompany(job.getCompany());
            dto.setExperienceLevel(job.getExperienceLevel());
            List<JobSkill> skills =
                    skillsByJobId.getOrDefault(
                            job.getId(),
                            Collections.emptyList()
                    );

            List<String> skillNames = new ArrayList<>();

            for (JobSkill jobSkill : skills) {
                skillNames.add(jobSkill.getSkill().getName());
            }

            dto.setSkills(skillNames);

            return dto;
        });
        return jobResponseDTOPage;
    }
}
