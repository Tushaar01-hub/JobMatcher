package com.jobMatcher.project.dtos;

import com.jobMatcher.project.entity.JobSkill;
import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.enums.ExperienceLevel;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class JobResponseDTO {
    private String title;
    private String description;
    private String company;
    private Integer salary;
    private ExperienceLevel experienceLevel;
    private List<String> skills;
}
