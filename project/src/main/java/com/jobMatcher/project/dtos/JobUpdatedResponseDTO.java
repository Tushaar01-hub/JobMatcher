package com.jobMatcher.project.dtos;

import com.jobMatcher.project.enums.ExperienceLevel;
import lombok.Data;

import java.util.List;

@Data
public class JobUpdatedResponseDTO {
    private String title;
    private String description;
    private String company;
    private Integer salary;
    private ExperienceLevel experienceLevel;
    private List<String> skills;
}
