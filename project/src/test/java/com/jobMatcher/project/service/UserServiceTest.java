package com.jobMatcher.project.service;

import com.jobMatcher.project.dtos.RegisterRequestDTO;
import com.jobMatcher.project.dtos.RegisterResponseDTO;
import com.jobMatcher.project.entity.User;
import com.jobMatcher.project.enums.AuthProvider;
import com.jobMatcher.project.enums.Role;
import com.jobMatcher.project.exception.DuplicateResourceException;
import com.jobMatcher.project.exception.ResourceNotFoundException;
import com.jobMatcher.project.repository.SkillRepository;
import com.jobMatcher.project.repository.UserRepository;
import com.jobMatcher.project.repository.UserSkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private UserSkillRepository userSkillRepository;

    @InjectMocks
    private UserService userService;


    @Test
    void registerUser_shouldCreateUser_whenEmailDoesNotExist() {

        // Arrange
        RegisterRequestDTO request =
                new RegisterRequestDTO(
                        "Tushaar",
                        "test@gmail.com",
                        "pass1234"
                );

        User user = new User();
        user.setName("Tushaar");
        user.setEmail("test@gmail.com");

        User savedUser = new User();
        savedUser.setId(1);
        savedUser.setName("Tushaar");
        savedUser.setEmail("test@gmail.com");
        savedUser.setRole(Role.USER);
        savedUser.setProvider(AuthProvider.LOCAL);

        RegisterResponseDTO response = new RegisterResponseDTO();
        response.setName("Tushaar");
        response.setEmail("test@gmail.com");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.empty());


        when(passwordEncoder.encode("pass1234"))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        when(modelMapper.map(savedUser, RegisterResponseDTO.class))
                .thenReturn(response);

        // Act
        RegisterResponseDTO result =
                userService.registerUser(request);

        // Assert
        assertEquals("Tushaar", result.getName());
        assertEquals("test@gmail.com", result.getEmail());

        verify(userRepository).findByEmail("test@gmail.com");
        verify(passwordEncoder).encode("pass1234");
        verify(userRepository).save(any(User.class));
    }


    @Test
    void registerUser_shouldThrowException_whenEmailAlreadyExists() {

        // Arrange
        RegisterRequestDTO request =
                new RegisterRequestDTO(
                        "Tushaar",
                        "test@gmail.com",
                        "pass1234"
                );

        User existingUser = new User();
        existingUser.setEmail("test@gmail.com");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(existingUser));

        // Act + Assert
        assertThrows(
                DuplicateResourceException.class,
                () -> userService.registerUser(request)
        );

        verify(userRepository).findByEmail("test@gmail.com");

        verify(userRepository, never())
                .save(any(User.class));
    }
}