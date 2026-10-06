package com.jobMatcher.project.controller;

import com.jobMatcher.project.dtos.JobResponseDTO;
import com.jobMatcher.project.dtos.JobSkillResponseDTO;
import com.jobMatcher.project.entity.JobSkill;
import com.jobMatcher.project.service.JobSkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs/{jobId}/skills")
public class JobSkillController {
    JobSkillService jobSkillService;

    public JobSkillController(JobSkillService jobSkillService) {
        this.jobSkillService = jobSkillService;
    }
    @PostMapping("/{skillId}")
    public ResponseEntity<JobSkillResponseDTO> addSkillToJob(@PathVariable long jobId,
                                                             @PathVariable long skillId){
        JobSkillResponseDTO jobSkill=jobSkillService.addSkillToJob(jobId, skillId);
        return new ResponseEntity<>(jobSkill, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<JobSkillResponseDTO>> getJobSkills(@PathVariable long jobId){
        return new ResponseEntity<>(jobSkillService.getJobSkills(jobId),HttpStatus.OK);
    }
    @DeleteMapping("/{skillId}")
    public ResponseEntity<Void> removeSkillFromJob(@PathVariable long jobId,@PathVariable long skillId){
        jobSkillService.removeSkillFromJob(jobId, skillId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
