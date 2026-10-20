package com.jobMatcher.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobMatcher.project.dtos.RegisterRequestDTO;
import com.jobMatcher.project.dtos.RegisterResponseDTO;
import com.jobMatcher.project.dtos.UserLoginRequestDTO;
import com.jobMatcher.project.security.JwtService;
import com.jobMatcher.project.service.UserService;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void register_shouldReturnCreated_whenRequestIsValid() throws Exception {

        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("Tushaar");
        request.setEmail("tushaar@gmail.com");
        request.setPassword("Password@123");

        RegisterResponseDTO response = new RegisterResponseDTO();

        when(userService.registerUser(any(RegisterRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated());

        verify(userService).registerUser(any(RegisterRequestDTO.class));
    }

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() throws Exception {

        UserLoginRequestDTO request = new UserLoginRequestDTO();
        request.setEmail("tushaar@gmail.com");
        request.setPassword("Password@123");

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenReturn(authentication);

        when(jwtService.generateToken(authentication))
                .thenReturn("test-jwt-token");

        mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("test-jwt-token"));

        verify(authenticationManager)
                .authenticate(any(Authentication.class));

        verify(jwtService)
                .generateToken(authentication);
    }
    @Test
    void register_shouldReturnBadRequest_whenPasswordIsInvalid() throws Exception {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("Tushaar");
        request.setEmail("tushaar@gmail.com");
        request.setPassword("password123");

        mockMvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest());

        verifyNoInteractions(userService);
    }
    @Test
    void login_shouldReturnBadRequest_whenPasswordIsInvalid() throws Exception {
        UserLoginRequestDTO request = new UserLoginRequestDTO();
        request.setEmail("tushaar@gmail.com");
        request.setPassword("password123");

        mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest());

        verifyNoInteractions(authenticationManager);
        verifyNoInteractions(jwtService);
    }
    @Test
    void login_shouldFail_whenCredentialsAreInvalid() throws Exception {
        UserLoginRequestDTO request = new UserLoginRequestDTO();
        request.setEmail("tushaar@gmail.com");
        request.setPassword("Wrong@123");

        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThrows(
                ServletException.class,
                () -> mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
        );

        verify(authenticationManager)
                .authenticate(any(Authentication.class));

        verifyNoInteractions(jwtService);
    }
}