package com.jobMatcher.project.entity;

import com.jobMatcher.project.enums.ExperienceLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String title;
    @NotBlank
    @Min(10) @Max(300)
    private String description;
    @NotBlank
    private String company;
    @NotBlank
    private int salary;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role is required") //an enum can have only 1 defined values
    private ExperienceLevel experienceLevel;

}
//id
// ├── title
// ├── description
// ├── company
// ├── location
// ├── salary
// └── experienceRequired
