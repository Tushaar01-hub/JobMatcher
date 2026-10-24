package com.jobMatcher.project.service;

import com.jobMatcher.project.dtos.RegisterRequestDTO;
import com.jobMatcher.project.dtos.RegisterResponseDTO;
import com.jobMatcher.project.dtos.UserLoginRequestDTO;
import com.jobMatcher.project.dtos.UserLoginResponseDTO;
import com.jobMatcher.project.exception.UserNotRegisteredException;
import com.jobMatcher.project.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.jobMatcher.project.exception.InvalidCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.BadCredentialsException;

@Service
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UserService userService,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public RegisterResponseDTO register(RegisterRequestDTO request) {
        request.setEmail(request.getEmail().trim().toLowerCase());
        return userService.registerUser(request);
    }

    public UserLoginResponseDTO login(UserLoginRequestDTO request) {
        String email = request.getEmail().trim().toLowerCase();
        request.setEmail(email);
        if (!userService.existsByEmail(request.getEmail())) {
            throw new UserNotRegisteredException("User isn't registered");
        }
        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            UsernamePasswordAuthenticationToken.unauthenticated(
                                    request.getEmail(),
                                    request.getPassword()
                            )
                    );

            String token = jwtService.generateToken(authentication);

            return new UserLoginResponseDTO(token);

        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException(
                    "Invalid credentials"
            );
        }
    }
}