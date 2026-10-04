package com.jobMatcher.project.controller;

import com.jobMatcher.project.dtos.JobResponseMatchDTO;
import com.jobMatcher.project.service.MatchingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class MatchingController {
    MatchingService matchingService;

    public MatchingController(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    @GetMapping("{userId}/matches")
    public ResponseEntity<List<JobResponseMatchDTO>> getUserMatchingJobs(@PathVariable long userId){
        List<JobResponseMatchDTO> jobResponseMatchDTOList=matchingService.getUserMatchingJob(userId);
        return new ResponseEntity<>(jobResponseMatchDTOList,HttpStatus.OK);
    }

}
