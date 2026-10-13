package com.jobMatcher.project.controller;

import com.jobMatcher.project.dtos.UserLoginRequestDTO;
import com.jobMatcher.project.dtos.UserLoginResponseDTO;
import com.jobMatcher.project.dtos.UserRequestDTO;
import com.jobMatcher.project.dtos.UserResponseDTO;
import com.jobMatcher.project.security.JwtService;
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

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(
            UserService userService,
            AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(
            @Valid @RequestBody UserRequestDTO userRequestDTO) {

        UserResponseDTO createdUser =
                userService.createUser(userRequestDTO);

        return new ResponseEntity<>(
                createdUser,
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public UserLoginResponseDTO login(

            @Valid @RequestBody UserLoginRequestDTO request) {
//        System.out.println("LOGIN CONTROLLER HIT");
//        UsernamePasswordAuthenticationToken token =
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                );
        Authentication authenticationRequest=UsernamePasswordAuthenticationToken
                .unauthenticated(
                        request.getEmail(),
                        request.getPassword()
                );

        Authentication authentication =
                authenticationManager.authenticate(authenticationRequest);

        String token= jwtService.generateToken(authentication);

        return new UserLoginResponseDTO(token);
    }
}
