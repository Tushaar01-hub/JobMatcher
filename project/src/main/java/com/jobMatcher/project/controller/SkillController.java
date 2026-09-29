package com.jobMatcher.project.controller;

import com.jobMatcher.project.dtos.SkillRequestDTO;
import com.jobMatcher.project.dtos.SkillResponseDTO;
import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
public class SkillController {
    SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

//    POST   /skills
    @PostMapping
    public ResponseEntity<SkillResponseDTO> createSkills(@Valid @RequestBody SkillRequestDTO skillRequestDTO){
        SkillResponseDTO skillResponseDto=skillService.createSkill(skillRequestDTO);
        return new ResponseEntity<>(skillResponseDto,HttpStatus.CREATED);
    }
//    GET    /skills
    @GetMapping
    public ResponseEntity<List<SkillResponseDTO>> getAllSkills(){
        List<SkillResponseDTO> skillResponseDTOList=skillService.getAllSkills();
        return new ResponseEntity<>(skillResponseDTOList,HttpStatus.OK);
    }
//    GET    /skills/{id}
    @GetMapping("/{id}")
    public ResponseEntity<SkillResponseDTO> getSkillById(@PathVariable long id){
        SkillResponseDTO skillResponseDTO=skillService.getSkillById(id);
        return new ResponseEntity<>(skillResponseDTO,HttpStatus.OK);
    }
//    PUT    /skills/{id}
    @PutMapping("/{id}")
    public ResponseEntity<SkillResponseDTO> updateSkill(@PathVariable long id,
                                             @Valid @RequestBody SkillRequestDTO skillRequestDTO){
        SkillResponseDTO skillResponseDTO=skillService.updateSkill(id,skillRequestDTO);
        return new ResponseEntity<>(skillResponseDTO,HttpStatus.OK);
    }
//    DELETE /skills/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSkill(@PathVariable long id){
        skillService.deleteSkill(id);
        return new ResponseEntity<>("Deleted Successfully",HttpStatus.NO_CONTENT);
    }
}
