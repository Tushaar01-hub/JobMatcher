package com.jobMatcher.project.controller;


import com.jobMatcher.project.entity.User;
import com.jobMatcher.project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    //    POST    createJob()        → 201 CREATED
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User createdUser=userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    //    GET     getAllJobs()       → 200 OK
    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users=userService.getAllUser();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
    //    GET     getJob()           → 200 OK
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id){
        User user=userService.getUser(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    //    PUT     updateJob()        → 200 OK
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id,
                                         @RequestBody User user){
        User updateduser=userService.updateUser(id,user);
        return new ResponseEntity<>(updateduser,HttpStatus.OK);
    }
    //    DELETE  deleteJob()        → 204 NO_CONTENT
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
