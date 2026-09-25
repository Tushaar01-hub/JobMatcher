package com.jobMatcher.project.dtos;

import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.entity.UserSkill;
import com.jobMatcher.project.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

public class UserRequestDTO {
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
    @NotEmpty
    private List<Skill> skills;
}
