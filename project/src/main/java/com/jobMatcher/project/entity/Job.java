package com.jobMatcher.project.entity;

import com.jobMatcher.project.enums.ExperienceLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "job")
    private List<JobSkill> jobSkills=new ArrayList<>();

//    @OneToOne
//    @JoinTable(
//            name = "JobSkill",
//            joinColumns=@JoinColumn(name = "job_id"),
//        inverseJoinColumns = @JoinColumn(name = "skill_id")
//    ))
//    private Skill skill;

}
//id
// ├── title
// ├── description
// ├── company
// ├── location
// ├── salary
// └── experienceRequired
