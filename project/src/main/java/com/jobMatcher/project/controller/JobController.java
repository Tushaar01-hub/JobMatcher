package com.jobMatcher.project.controller;

import com.jobMatcher.project.dtos.JobRequestDTO;
import com.jobMatcher.project.dtos.JobResponseDTO;
import com.jobMatcher.project.dtos.JobUpdateRequestDTO;
import com.jobMatcher.project.dtos.JobUpdatedResponseDTO;
import com.jobMatcher.project.entity.Job;
import com.jobMatcher.project.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Job")
public class JobController {
    JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
//    POST    createJob()        → 201 CREATED
    @PostMapping
    public ResponseEntity<JobResponseDTO> createJob(@RequestBody JobRequestDTO jobRequestDTO){
        JobResponseDTO savedJob=jobService.createJob(jobRequestDTO);
        return new ResponseEntity<>(savedJob, HttpStatus.CREATED);
    }
//    GET     getAllJobs()       → 200 OK
    @GetMapping
    public ResponseEntity<List<JobResponseDTO>> getAllJob(){
        List<JobResponseDTO> jobsResponses=jobService.getAllJobs();
        return new ResponseEntity<>(jobsResponses,HttpStatus.OK);
    }
//    GET     getJob()           → 200 OK
    @GetMapping("/{id}")
    public ResponseEntity<JobResponseDTO> getJob(@PathVariable long id){
        JobResponseDTO jobResponseDTO=jobService.getJob(id);
        return new ResponseEntity<>(jobResponseDTO,HttpStatus.OK);
    }
//    PUT     updateJob()        → 200 OK
    @PutMapping("/{id}")
    public ResponseEntity<JobUpdatedResponseDTO> updateJob(@PathVariable long id,
                                                         @RequestBody JobUpdateRequestDTO jobUpdateRequestDTO){
        JobUpdatedResponseDTO updatedjobResponseDTO=jobService.updateJob(id,jobUpdateRequestDTO);
        return new ResponseEntity<>(updatedjobResponseDTO,HttpStatus.OK);
    }
//    DELETE  deleteJob()        → 204 NO_CONTENT
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable long id){
        jobService.deleteJob(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
