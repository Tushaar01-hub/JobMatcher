package com.jobMatcher.project.controller;

import com.jobMatcher.project.dtos.JobRequestDTO;
import com.jobMatcher.project.dtos.JobResponseDTO;
import com.jobMatcher.project.dtos.JobUpdateRequestDTO;
import com.jobMatcher.project.dtos.JobUpdatedResponseDTO;
import com.jobMatcher.project.enums.ExperienceLevel;
import com.jobMatcher.project.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {
    JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
//    POST    createJob()        → 201 CREATED
    @PostMapping
    public ResponseEntity<JobResponseDTO> createJob(@Valid @RequestBody JobRequestDTO jobRequestDTO){
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
    //GET /jobs/search?title=java
    @GetMapping("/search")
    public ResponseEntity<List<JobResponseDTO>> searchByTitle(@RequestParam String title){
        List<JobResponseDTO> jobResponseDTOList=jobService.searchJobByTitle(title);
        return new ResponseEntity<>(jobResponseDTOList,HttpStatus.OK);
    }
    //GET /jobs/filter?experienceLevel=FRESHER
    @GetMapping("/filter/experiencelevel")
    public ResponseEntity<List<JobResponseDTO>> searchByExperienceLevel(@RequestParam ExperienceLevel explvl){
        List<JobResponseDTO> jobResponseDTOList=jobService.searchByExperienceLevel(explvl);
        return new ResponseEntity<>(jobResponseDTOList,HttpStatus.OK);
    }
//    GET /jobs/filter?minSalary=800000
@GetMapping("/filter/salary")
public ResponseEntity<List<JobResponseDTO>> searchBySalary(@RequestParam("minSalary") long salary){
    List<JobResponseDTO> jobResponseDTOList=jobService.searchBySalary(salary);
    return new ResponseEntity<>(jobResponseDTOList,HttpStatus.OK);
}
//    GET /jobs/filter/company
@GetMapping("/filter/company")
public ResponseEntity<List<JobResponseDTO>> searchByCompany(@RequestParam String company){
    List<JobResponseDTO> jobResponseDTOList=jobService.searchByCompany(company);
    return new ResponseEntity<>(jobResponseDTOList,HttpStatus.OK);
}
    //    GET /jobs/filter/skill
    @GetMapping("/filter/skill")
    public ResponseEntity<List<JobResponseDTO>> searchBySkill(@RequestParam String skill){
        List<JobResponseDTO> jobResponseDTOList=jobService.searchBySkill(skill);
        return new ResponseEntity<>(jobResponseDTOList,HttpStatus.OK);
    }
//    PUT     updateJob()        → 200 OK
    @PutMapping("/{id}")
    public ResponseEntity<JobUpdatedResponseDTO> updateJob(@PathVariable long id,
                                                        @Valid @RequestBody JobUpdateRequestDTO jobUpdateRequestDTO){
        JobUpdatedResponseDTO updatedjobResponseDTO=jobService.updateJob(id,jobUpdateRequestDTO);
        return new ResponseEntity<>(updatedjobResponseDTO,HttpStatus.OK);
    }
//    DELETE  deleteJob()        → 204 NO_CONTENT
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable long id){
        jobService.deleteJob(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //GET /jobs?title=java&experienceLevel=FRESHER&minSalary=800000&company=CloudStack&skill=Java
    @GetMapping("/filters")
    public ResponseEntity<List<JobResponseDTO>> getJobsByFilter(@RequestParam(required = false) String title,
                                                                @RequestParam(required = false) ExperienceLevel experienceLevel,
                                                                @RequestParam(required = false) Long minSalary,
                                                                @RequestParam(required = false) String company,
                                                                @RequestParam(required = false) String skillName){
        List<JobResponseDTO> jobResponseDTOList=jobService.getJobsByFilter(title, experienceLevel, minSalary, company, skillName);
        return new ResponseEntity<>(jobResponseDTOList,HttpStatus.OK);
    }

}
