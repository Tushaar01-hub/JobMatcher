package com.jobMatcher.project.dtos;

import lombok.Data;

@Data
public class UserLoginResponseDTO {
    String accessToken;

    public UserLoginResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
