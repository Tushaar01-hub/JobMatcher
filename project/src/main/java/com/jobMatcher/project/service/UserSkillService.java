package com.jobMatcher.project.service;

import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.entity.User;
import com.jobMatcher.project.entity.UserSkill;
import com.jobMatcher.project.repository.SkillRepository;
import com.jobMatcher.project.repository.UserRepository;
import com.jobMatcher.project.repository.UserSkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSkillService {
    UserRepository userRepository;
    SkillRepository skillRepository;
    UserSkillRepository userSkillRepository;

    public UserSkillService(UserRepository userRepository, SkillRepository skillRepository, UserSkillRepository userSkillRepository) {
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
        this.userSkillRepository = userSkillRepository;
    }

    //    1. addSkillToUser()
    //will be returning userskillResponse
    public User addSkillToUser(long userId,long skillId){
        if(userSkillRepository.existsByUserIdAndSkillId(userId,skillId)){
            throw new RuntimeException("User with that Skill exists already");
        }
        UserSkill userSkill=new UserSkill();
        User user=userRepository.findById(userId).orElseThrow();
        Skill skill=skillRepository.findById(skillId).orElseThrow();
        userSkill.setUser(user);
        userSkill.setSkill(skill);
        userSkillRepository.save(userSkill);
        return user;
    }
//2. getUserSkills()
    public List<UserSkill> getUserSkills(long userId){
//        if(userSkillRepository.existsByUserId(userId)){
//            List<Long> skillsId=userSkillRepository.findAllByUserId(userId);
//            return skillsId;
//        }
        return userSkillRepository.findAllByUserId(userId);
    }
//3. removeSkillFromUser()
    public void removeSkillFromUser(long userId,long skillId){
        UserSkill userSkill=userSkillRepository.findByUserIdAndSkillId(userId,skillId);
        if (userSkill == null) {
            throw new RuntimeException("User does not have this skill");
        }
        long userskillId=userSkill.getId();
        userSkillRepository.deleteById(userskillId);
    }
}
