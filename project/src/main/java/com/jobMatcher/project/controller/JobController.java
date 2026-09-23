package com.jobMatcher.project.controller;

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
    public ResponseEntity<Job> createJob(@RequestBody Job job){
        Job savedJob=jobService.createJob(job);
        return new ResponseEntity<>(savedJob, HttpStatus.CREATED);
    }
//    GET     getAllJobs()       → 200 OK
    @GetMapping
    public ResponseEntity<List<Job>> getAllJob(){
        List<Job> jobs=jobService.getAllJobs();
        return new ResponseEntity<>(jobs,HttpStatus.OK);
    }
//    GET     getJob()           → 200 OK
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJob(@PathVariable long id){
        Job job=jobService.getJob(id);
        return new ResponseEntity<>(job,HttpStatus.OK);
    }
//    PUT     updateJob()        → 200 OK
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable long id,
                                         @RequestBody Job job){
        Job updatedjob=jobService.updateJob(id,job);
        return new ResponseEntity<>(updatedjob,HttpStatus.OK);
    }
//    DELETE  deleteJob()        → 204 NO_CONTENT
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable long id){
        jobService.deleteJob(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
