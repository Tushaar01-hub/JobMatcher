package com.jobMatcher.project.dtos;
import com.jobMatcher.project.enums.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class UserRequestDTO {
    @NotBlank(message = "Name can't be empty")
    private String name;
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,32}$",
            message = "Password must be 8-32 characters and contain atleast 1 uppercase, lowercase, digit, and special character"
    )
    private String password;
    @NotEmpty(message = "Skills can't be empty")
    private List<Long> skillIds;
}
