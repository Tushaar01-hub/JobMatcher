package com.jobMatcher.project.controller;

import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.service.SkillService;
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
    public ResponseEntity<Skill> createSkills(@RequestBody Skill skill){
        Skill skill1=skillService.createSkill(skill);
        return new ResponseEntity<>(skill1,HttpStatus.CREATED);
    }
//    GET    /skills
    @GetMapping
    public ResponseEntity<List<Skill>> getAllSkills(){
        List<Skill> skills=skillService.getAllSkills();
        return new ResponseEntity<>(skills,HttpStatus.OK);
    }
//    GET    /skills/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable long id){
        Skill skill=skillService.getSkillById(id).orElseThrow(()->new RuntimeException("Skill Not found"));
        return new ResponseEntity<>(skill,HttpStatus.OK);
    }
//    PUT    /skills/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable long id,
                                             @RequestBody Skill skill){
        Skill skill1=skillService.updateSkill(id,skill);
        return new ResponseEntity<>(skill1,HttpStatus.OK);
    }
//    DELETE /skills/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable long id){
        skillService.deleteSkill(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
