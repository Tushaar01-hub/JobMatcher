package com.jobMatcher.project.controller;


import com.jobMatcher.project.dtos.UserSkillResponseDTO;
import com.jobMatcher.project.entity.UserSkill;
import com.jobMatcher.project.service.UserSkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/skills")
public class UserSkillController {
    UserSkillService userSkillService;

    public UserSkillController(UserSkillService userSkillService) {
        this.userSkillService = userSkillService;
    }
//    /users/{userId}/skills/{skillId}
    @PostMapping("/{skillId}")
    public ResponseEntity<UserSkillResponseDTO> addSkillToUser(@PathVariable long userId,
                                                               @PathVariable long skillId){
        UserSkillResponseDTO userSkillResponseDTO=userSkillService.addSkillToUser(userId,skillId);
        return new ResponseEntity<>(userSkillResponseDTO, HttpStatus.CREATED);

    }
    @GetMapping
    public ResponseEntity<List<UserSkillResponseDTO>> getUserSkills(@PathVariable long userId){
        List<UserSkillResponseDTO> userSkills=userSkillService.getUserSkills(userId);
        return new ResponseEntity<>(userSkills,HttpStatus.OK);
    }
    @DeleteMapping("/{skillId}")
    public ResponseEntity<Void> removeSkillFromUser(@PathVariable long userId,
                                                    @PathVariable long skillId){
        userSkillService.removeSkillFromUser(userId, skillId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
