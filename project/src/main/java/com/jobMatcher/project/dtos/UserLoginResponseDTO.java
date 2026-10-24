package com.jobMatcher.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLoginResponseDTO {
    String accessToken;

    public UserLoginResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
