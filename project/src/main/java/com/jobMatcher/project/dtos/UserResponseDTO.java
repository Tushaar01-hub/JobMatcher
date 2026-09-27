package com.jobMatcher.project.dtos;

import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.entity.UserSkill;
import com.jobMatcher.project.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserResponseDTO {
    private String name;
    private String email;
    private Role role;
    private List<String> Skills;
}
