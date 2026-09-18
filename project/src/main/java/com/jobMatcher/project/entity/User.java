package com.jobMatcher.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Name can't be empty")
    private String name;
    @Email
    private String email;
    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,32}$",
            message = "Password must be 8-32 characters and contain atleast 1 uppercase, lowercase, digit, and special character"
    )
    private String password;
//    @Size(min = 10,max = 10,message = "Phone field should be of 10 digits")
//    private long phone;
//    @NotBlank
//    private String collegename;
//    private int graduationYear;
//    private LocalTime createdAt;
//    private LocalTime updatedAt;


}
//id
//name
//email
//password
//phone
//college
//graduationYear
//createdAt
//updatedAt
