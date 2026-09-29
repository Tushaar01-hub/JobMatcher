package com.jobMatcher.project.dtos;

import lombok.Data;

@Data
public class JobSkillResponseDTO {
    long jobId;
    long skillId;
    String skillName;
//    public JobSkillResponseDTO(long skillId,String skillName){
//        this.skillId=skillId;
//        this.skillName=skillName;
//    }

}
