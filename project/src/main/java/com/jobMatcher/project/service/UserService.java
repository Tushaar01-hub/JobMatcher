package com.jobMatcher.project.service;

import com.jobMatcher.project.dtos.SkillResponseDTO;
import com.jobMatcher.project.dtos.UserRequestDTO;
import com.jobMatcher.project.dtos.UserResponseDTO;
import com.jobMatcher.project.dtos.UserUpdatedRequestDTO;
import com.jobMatcher.project.exception.DuplicateResourceException;
import com.jobMatcher.project.exception.ResourceNotFoundException;
import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.entity.User;
import com.jobMatcher.project.entity.UserSkill;
import com.jobMatcher.project.repository.SkillRepository;
import com.jobMatcher.project.repository.UserRepository;
import com.jobMatcher.project.repository.UserSkillRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;
    private final ModelMapper modelMapper;
    UserSkillRepository userSkillRepository;
    SkillRepository skillRepository;

    public UserService(ModelMapper modelMapper, UserRepository userRepository, UserSkillRepository userSkillRepository, SkillRepository skillRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.userSkillRepository = userSkillRepository;
        this.skillRepository = skillRepository;
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){
        User user=modelMapper.map(userRequestDTO,User.class);
        if(userRepository.existsByEmail(user.getEmail())){
            throw new DuplicateResourceException("User Exists");
        }
        List<Skill> skillList=userRequestDTO.getSkillIds().stream().map(skillId->skillRepository.findById(skillId).orElseThrow(()->new ResourceNotFoundException("Skill Not Found"))).toList();
        User savedUser=userRepository.save(user);
        List<SkillResponseDTO> skillResponseDTOList=new ArrayList<>();

        for(Skill skill:skillList){
            UserSkill userSkill=new UserSkill();
            userSkill.setUser(savedUser);
//            Skill skill=skillRepository.findById(skilllId).orElseThrow(()->new ResourceNotFoundException("Skill Not Found"));
            userSkill.setSkill(skill);
            SkillResponseDTO skillResponseDTO=new SkillResponseDTO();
            skillResponseDTO.setId(skill.getId());
            skillResponseDTO.setName(skill.getName());
            skillResponseDTOList.add(skillResponseDTO);
            userSkillRepository.save(userSkill);
        }
        UserResponseDTO userResponseDTO=modelMapper.map(savedUser,UserResponseDTO.class);
        userResponseDTO.setSkills(skillResponseDTOList);
        return userResponseDTO;
    }
    public UserResponseDTO getUser(long id){
        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
        UserResponseDTO userResponseDTO=modelMapper.map(user,UserResponseDTO.class);
        List<UserSkill> userSkillList=userSkillRepository.findAllByUserId(user.getId());
        List<SkillResponseDTO> skillList=new ArrayList<>();
        for(UserSkill userSkill:userSkillList){
            Skill skill=userSkill.getSkill();
            SkillResponseDTO skillResponseDTO=new SkillResponseDTO();
            skillResponseDTO.setName(skill.getName());
            skillResponseDTO.setId(skill.getId());
            skillList.add(skillResponseDTO);
        }
        userResponseDTO.setSkills(skillList);
        return userResponseDTO;
    }

    public List<UserResponseDTO> getAllUser(){
        List<User> userList=userRepository.findAll();
        List<UserResponseDTO> userResponseDTOList=new ArrayList<>();

        for(User user:userList){
            UserResponseDTO userResponseDTO=modelMapper.map(user,UserResponseDTO.class);
            List<SkillResponseDTO> skillResponseDTOList=new ArrayList<>();
//            List<String> userSkills=new ArrayList<>();
            for(UserSkill userSkill:userSkillRepository.findAllByUserId(user.getId())){
//                userSkills.add(userSkill.getSkill().getName());
                Skill skill=userSkill.getSkill();
                SkillResponseDTO skillResponseDTO=new SkillResponseDTO();
                skillResponseDTO.setId(skill.getId());
                skillResponseDTO.setName(skill.getName());
                skillResponseDTOList.add(skillResponseDTO);
            }
            userResponseDTO.setSkills(skillResponseDTOList);
            userResponseDTOList.add(userResponseDTO);
        }
        return userResponseDTOList;
    }


    public UserResponseDTO updateUser(long id, UserUpdatedRequestDTO userUpdatedRequestDTO){
        User user=modelMapper.map(userUpdatedRequestDTO,User.class);
        User u=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
        u.setEmail(user.getEmail());
        u.setName(user.getName());
        u.setPassword(user.getPassword());
//        u.setRole(user.getRole()); will check at authorization
        User savedUSER=userRepository.save(u);
        UserResponseDTO userResponseDTO=modelMapper.map(savedUSER,UserResponseDTO.class);
        List<SkillResponseDTO> skillResponseDTOList=new ArrayList<>();
        List<UserSkill> userSkillList=userSkillRepository.findAllByUserId(savedUSER.getId());
        for(UserSkill userSkill:userSkillList){
            Skill skill=userSkill.getSkill();
            SkillResponseDTO skillResponseDTO=new SkillResponseDTO();
            skillResponseDTO.setName(skill.getName());
            skillResponseDTO.setId(skill.getId());
            skillResponseDTOList.add(skillResponseDTO);
        }
        userResponseDTO.setSkills(skillResponseDTOList);
        return userResponseDTO;

    }
    public void deleteUser(long id){
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("User Not Found");
        }
        userRepository.deleteById(id);
    }
}
