package com.jobMatcher.project.dtos;

import jakarta.validation.constraints.NotBlank;

public class SkillDTO {
    @NotBlank
    String name;
}
