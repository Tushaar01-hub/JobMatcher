package com.jobMatcher.project.service;

import com.jobMatcher.project.entity.Job;
import com.jobMatcher.project.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job createJob(Job job){
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs(){
        return jobRepository.findAll();
    }

    public Job getJob(long id){
        Job j=jobRepository.findById(id).orElseThrow(()->new RuntimeException("job not found"));
        return j;
    }

    public Job updateJob(long id,Job job){
        Job j=jobRepository.findById(id).orElseThrow(()->new RuntimeException("job not found"));
        j.setCompany(job.getCompany());
        j.setDescription(job.getDescription());
        j.setSalary(job.getSalary());
        j.setTitle(job.getTitle());
        j.setExperienceLevel(job.getExperienceLevel());
        return jobRepository.save(j);
    }
    public void deleteJob(long id){
        jobRepository.deleteById(id);
    }
}
