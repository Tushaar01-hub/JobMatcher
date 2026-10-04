package com.jobMatcher.project.dtos;

import lombok.Data;

import java.util.List;

@Data
public class JobResponseMatchDTO {
    private long jobId;
    private String title;
    private String company;
    private double matchPercentage;
    private List<String> matchedSkills;
    private List<String> missingSkills;

}
