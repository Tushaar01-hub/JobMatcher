package com.jobMatcher.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobMatcher.project.dtos.RegisterRequestDTO;
import com.jobMatcher.project.dtos.RegisterResponseDTO;
import com.jobMatcher.project.dtos.UserLoginRequestDTO;
import com.jobMatcher.project.dtos.UserLoginResponseDTO;
import com.jobMatcher.project.exception.InvalidCredentialsException;
import com.jobMatcher.project.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private AuthService authService;

    @Test
    void register_shouldReturnCreated_whenRequestIsValid() throws Exception {

        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("Tushaar");
        request.setEmail("tushaar@gmail.com");
        request.setPassword("Password@123");

        RegisterResponseDTO response = new RegisterResponseDTO();

        when(authService.register(any(RegisterRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated());

        verify(authService).register(any(RegisterRequestDTO.class));
    }

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() throws Exception {

        UserLoginRequestDTO request = new UserLoginRequestDTO();
        request.setEmail("tushaar@gmail.com");
        request.setPassword("Password@123");

        UserLoginResponseDTO response = new UserLoginResponseDTO();
        response.setAccessToken("test-jwt-token");

        when(authService.login(any(UserLoginRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("test-jwt-token"));

        verify(authService).login(any(UserLoginRequestDTO.class));
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

        verifyNoInteractions(authService);
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

        verifyNoInteractions(authService);
    }

    @Test
    void login_shouldFail_whenCredentialsAreInvalid() throws Exception {

        UserLoginRequestDTO request = new UserLoginRequestDTO();
        request.setEmail("tushaar@gmail.com");
        request.setPassword("Wrong@123");

        when(authService.login(any(UserLoginRequestDTO.class)))
                .thenThrow(new InvalidCredentialsException("Invalid credentials"));

        mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isUnauthorized());

        verify(authService).login(any(UserLoginRequestDTO.class));
    }
}