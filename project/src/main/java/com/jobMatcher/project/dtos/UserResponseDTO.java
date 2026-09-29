package com.jobMatcher.project.dtos;
import com.jobMatcher.project.enums.Role;
import lombok.Data;
import java.util.List;
@Data
public class UserResponseDTO {
    private String name;
    private String email;
    private Role role;
    private List<SkillResponseDTO> skills;
}
