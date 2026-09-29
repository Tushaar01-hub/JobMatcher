package com.jobMatcher.project.service;

import com.jobMatcher.project.dtos.UserSkillResponseDTO;
import com.jobMatcher.project.exception.DuplicateResourceException;
import com.jobMatcher.project.exception.ResourceNotFoundException;
import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.entity.User;
import com.jobMatcher.project.entity.UserSkill;
import com.jobMatcher.project.repository.SkillRepository;
import com.jobMatcher.project.repository.UserRepository;
import com.jobMatcher.project.repository.UserSkillRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSkillService {
    UserRepository userRepository;
    SkillRepository skillRepository;
    UserSkillRepository userSkillRepository;
//    ModelMapper modelMapper;

    public UserSkillService(UserRepository userRepository, SkillRepository skillRepository, UserSkillRepository userSkillRepository) {
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
        this.userSkillRepository = userSkillRepository;

    }

    //    1. addSkillToUser()
    //will be returning userskillResponse
    public UserSkillResponseDTO addSkillToUser(long userId, long skillId){
        if(userSkillRepository.existsByUserIdAndSkillId(userId,skillId)){
            throw new DuplicateResourceException("User with that Skill exists already");
        }
        UserSkill userSkill=new UserSkill();
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
        Skill skill=skillRepository.findById(skillId).orElseThrow(()->new ResourceNotFoundException("Skill Not Found"));
        userSkill.setUser(user);
        userSkill.setSkill(skill);
        UserSkill savedUserSkill=userSkillRepository.save(userSkill);
        UserSkillResponseDTO userSkillResponseDTO=new UserSkillResponseDTO();
        userSkillResponseDTO.setUserId(savedUserSkill.getUser().getId());
        userSkillResponseDTO.setSkillName(savedUserSkill.getSkill().getName());
        userSkillResponseDTO.setSkillId(savedUserSkill.getSkill().getId());
//        UserSkillResponseDTO userSkillResponseDTO=modelMapper.map(savedUserSkill,UserSkillResponseDTO.class);
        return userSkillResponseDTO;
    }
//2. getUserSkills()
    public List<UserSkillResponseDTO> getUserSkills(long userId){
//        if(userSkillRepository.existsByUserId(userId)){
//            List<Long> skillsId=userSkillRepository.findAllByUserId(userId);
//            return skillsId;
//        }
        List<UserSkill> userSkillList=userSkillRepository.findAllByUserId(userId);
        List<UserSkillResponseDTO> userSkillResponseDTOS=new ArrayList<>();
        for(UserSkill userSkill:userSkillList){
            UserSkillResponseDTO userSkillResponseDTO=new UserSkillResponseDTO();
            userSkillResponseDTO.setUserId(userSkill.getUser().getId());
            userSkillResponseDTO.setSkillName(userSkill.getSkill().getName());
            userSkillResponseDTO.setSkillId(userSkill.getSkill().getId());
            userSkillResponseDTOS.add(userSkillResponseDTO);
        }
        return userSkillResponseDTOS;
    }
//3. removeSkillFromUser()
    public void removeSkillFromUser(long userId,long skillId){
        UserSkill userSkill=userSkillRepository.findByUserIdAndSkillId(userId,skillId);
        if (userSkill == null) {
            throw new ResourceNotFoundException("User with that sSkill doesn't Exists");
        }
        long userskillId=userSkill.getId();
        userSkillRepository.deleteById(userskillId);
    }
}
