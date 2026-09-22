package com.jobMatcher.project.service;

import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {
    SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    //    1. createSkill()
    public Skill createSkill(Skill skill){
        return skillRepository.save(skill);
    }
//2. getAllSkills()
    public List<Skill> getAllSkills(){
        List<Skill> skills=skillRepository.findAll();
        return skills;
    }
//3. getSkillById()
    public Optional<Skill> getSkillById(long id){
       Optional<Skill> skill= skillRepository.findById(id);

       return skill;
    }
//4. updateSkill()
    public Skill updateSkill(long id,Skill skill){
//        Optional<Skill> sk=skillRepository.findById(id);
//        if(sk.isPresent()){
//            Skill existingSkill=sk.get();
//            existingSkill.setName(skill.getName());
//            return skillRepository.save(existingSkill);
//        }
//        return null;
        Skill existingSkill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        existingSkill.setName(skill.getName());

        return skillRepository.save(existingSkill);

    }
//5. deleteSkill()
    public void deleteSkill(long id){
        skillRepository.deleteById(id);
    }
}
