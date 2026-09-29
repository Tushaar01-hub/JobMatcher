package com.jobMatcher.project.dtos;

import lombok.Data;

@Data
public class UserSkillResponseDTO {
    long userId;
    long skillId;
    String skillName;

}
