package com.jobMatcher.project.entity;

import com.jobMatcher.project.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
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
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Experience level is required")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<UserSkill> userSkills=new ArrayList<>();
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
