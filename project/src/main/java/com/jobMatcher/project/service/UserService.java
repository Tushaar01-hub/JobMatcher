package com.jobMatcher.project.service;

import com.jobMatcher.project.entity.User;
import com.jobMatcher.project.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User createUser(User user){
        return userRepository.save(user);
    }
    public User getUser(long id){
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("user not found"));
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }
    public User updateUser(long id,User user){
        User u=userRepository.findById(id).orElseThrow(()->new RuntimeException());
        u.setEmail(user.getEmail());
        u.setName(user.getName());
        u.setPassword(user.getPassword());
//        u.setRole(user.getRole()); will check at authorization
        return userRepository.save(u);
    }
    public void deleteUser(long id){
        userRepository.deleteById(id);
    }
}
