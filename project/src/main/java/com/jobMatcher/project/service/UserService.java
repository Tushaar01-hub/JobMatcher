package com.jobMatcher.project.service;

import com.jobMatcher.project.dtos.UserRequestDTO;
import com.jobMatcher.project.dtos.UserResponseDTO;
import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.entity.User;
import com.jobMatcher.project.entity.UserSkill;
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

    public UserService(ModelMapper modelMapper, UserRepository userRepository, UserSkillRepository userSkillRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.userSkillRepository = userSkillRepository;
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){
        User user=modelMapper.map(userRequestDTO,User.class);
        User savedUser=userRepository.save(user);
        UserResponseDTO userResponseDTO=modelMapper.map(savedUser,UserResponseDTO.class);
        List<UserSkill> userSkills=userSkillRepository.findAllByUserId(savedUser.getId());
        List<String> userResponseDTOList=new ArrayList<>();
        for (UserSkill userSkill:userSkills) {
            userResponseDTOList.add(userSkill.getSkill().getName());
        }
        userResponseDTO.setSkills(userResponseDTOList);

        return userResponseDTO;
    }
    public UserResponseDTO getUser(long id){
        User user=userRepository.findById(id).orElseThrow();
        UserResponseDTO userResponseDTO=modelMapper.map(user,UserResponseDTO.class);
        return userResponseDTO;
    }

    public List<UserResponseDTO> getAllUser(){
        List<User> userList=userRepository.findAll();
        List<UserResponseDTO> userResponseDTOList=new ArrayList<>();
        for(User user:userList){
            UserResponseDTO userResponseDTO=new UserResponseDTO();
            userResponseDTO.setRole(user.getRole());
            userResponseDTO.setName(user.getName());
            userResponseDTO.setEmail(user.getEmail());
            List<String> userSkills=new ArrayList<>();
            for(UserSkill userSkill:userSkillRepository.findAllByUserId(user.getId())){
                userSkills.add(userSkill.getSkill().getName());
                userResponseDTO.setSkills(userSkills);
            }
            userResponseDTOList.add(userResponseDTO);
        }
        return userResponseDTOList;
    }


    public UserResponseDTO updateUser(long id,UserRequestDTO userRequestDTO){
        User user=modelMapper.map(userRequestDTO,User.class);
        User u=userRepository.findById(id).orElseThrow(()->new RuntimeException());
        u.setEmail(user.getEmail());
        u.setName(user.getName());
        u.setPassword(user.getPassword());
//        u.setRole(user.getRole()); will check at authorization
        User savedUSER=userRepository.save(u);
        UserResponseDTO userResponseDTO=modelMapper.map(savedUSER,UserResponseDTO.class);
        return userResponseDTO;

    }
    public void deleteUser(long id){
        userRepository.deleteById(id);
    }
}
