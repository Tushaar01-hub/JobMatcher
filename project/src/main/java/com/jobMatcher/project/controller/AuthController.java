package com.jobMatcher.project.controller;

import com.jobMatcher.project.dtos.*;
import com.jobMatcher.project.security.JwtService;
import com.jobMatcher.project.service.AuthService;
import com.jobMatcher.project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(
            @Valid @RequestBody RegisterRequestDTO request) {

        return new ResponseEntity<>(
                authService.register(request),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(
            @Valid @RequestBody UserLoginRequestDTO request) {

        return ResponseEntity.ok(authService.login(request));
    }
}
