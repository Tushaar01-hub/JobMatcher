package com.jobMatcher.project.dtos;

import com.jobMatcher.project.enums.ExperienceLevel;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JobUpdateRequestDTO {
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
//    @Enumerated(EnumType.STRING)
    @NotNull(message = "Experience is required") //an enum can have only 1 defined values
    private ExperienceLevel experienceLevel;
}
