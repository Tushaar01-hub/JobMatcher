package com.jobMatcher.project.dtos;

import com.jobMatcher.project.entity.JobSkill;
import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.enums.ExperienceLevel;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class JobRequestDTO {
    @NotBlank
    private String title;
    @NotBlank
    @Size(min = 10, max = 300)
    private String description;
    @NotBlank
    private String company;
    //    @NotBlank-doesnt work on int
    @NotNull
    @Min(0)
    private Integer salary;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role is required") //an enum can have only 1 defined values
    private ExperienceLevel experienceLevel;
    @NotEmpty
    private List<Long> skills;
}
