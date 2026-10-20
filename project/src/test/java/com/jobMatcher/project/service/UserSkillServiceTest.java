package com.jobMatcher.project.service;

import com.jobMatcher.project.dtos.UserSkillResponseDTO;
import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.entity.User;
import com.jobMatcher.project.entity.UserSkill;
import com.jobMatcher.project.exception.DuplicateResourceException;
import com.jobMatcher.project.exception.ResourceNotFoundException;
import com.jobMatcher.project.repository.SkillRepository;
import com.jobMatcher.project.repository.UserRepository;
import com.jobMatcher.project.repository.UserSkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSkillServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private UserSkillRepository userSkillRepository;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    private UserSkillService userSkillService;

    @BeforeEach
    void setUp() {
        userSkillService = new UserSkillService(
                redisTemplate,
                userSkillRepository,
                skillRepository,
                userRepository
        );
    }

    @Test
    void addSkillToUser_shouldAddSkillSuccessfully() {

        long userId = 1L;
        long skillId = 2L;

        User user = new User();
        user.setId(userId);

        Skill skill = new Skill();
        skill.setId(skillId);
        skill.setName("Java");

        UserSkill savedUserSkill = new UserSkill();
        savedUserSkill.setId(10L);
        savedUserSkill.setUser(user);
        savedUserSkill.setSkill(skill);

        when(userSkillRepository.existsByUserIdAndSkillId(userId, skillId))
                .thenReturn(false);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(skillRepository.findById(skillId))
                .thenReturn(Optional.of(skill));

        when(userSkillRepository.save(any(UserSkill.class)))
                .thenReturn(savedUserSkill);

        UserSkillResponseDTO result =
                userSkillService.addSkillToUser(userId, skillId);

        assertEquals(userId, result.getUserId());
        assertEquals(skillId, result.getSkillId());
        assertEquals("Java", result.getSkillName());

        verify(userSkillRepository).save(any(UserSkill.class));
        verify(redisTemplate).delete("users:1:matches");
    }

    @Test
    void addSkillToUser_shouldThrowDuplicate_whenSkillAlreadyExists() {

        long userId = 1L;
        long skillId = 2L;

        when(userSkillRepository.existsByUserIdAndSkillId(userId, skillId))
                .thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> userSkillService.addSkillToUser(userId, skillId)
        );

        verify(userRepository, never()).findById(userId);
        verify(skillRepository, never()).findById(skillId);
        verify(userSkillRepository, never()).save(any(UserSkill.class));
    }

    @Test
    void addSkillToUser_shouldThrowNotFound_whenUserDoesNotExist() {

        long userId = 1L;
        long skillId = 2L;

        when(userSkillRepository.existsByUserIdAndSkillId(userId, skillId))
                .thenReturn(false);

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> userSkillService.addSkillToUser(userId, skillId)
        );

        verify(skillRepository, never()).findById(skillId);
        verify(userSkillRepository, never()).save(any(UserSkill.class));
    }

    @Test
    void getUserSkills_shouldReturnUserSkills() {

        long userId = 1L;

        User user = new User();
        user.setId(userId);

        Skill skill = new Skill();
        skill.setId(2L);
        skill.setName("Java");

        UserSkill userSkill = new UserSkill();
        userSkill.setUser(user);
        userSkill.setSkill(skill);

        when(userSkillRepository.findAllByUserId(userId))
                .thenReturn(List.of(userSkill));

        List<UserSkillResponseDTO> result =
                userSkillService.getUserSkills(userId);

        assertEquals(1, result.size());
        assertEquals(userId, result.get(0).getUserId());
        assertEquals(2L, result.get(0).getSkillId());
        assertEquals("Java", result.get(0).getSkillName());

        verify(userSkillRepository).findAllByUserId(userId);
    }

    @Test
    void removeSkillFromUser_shouldRemoveSkillSuccessfully() {

        long userId = 1L;
        long skillId = 2L;

        UserSkill userSkill = new UserSkill();
        userSkill.setId(10L);

        when(userSkillRepository.findByUserIdAndSkillId(userId, skillId))
                .thenReturn(userSkill);

        userSkillService.removeSkillFromUser(userId, skillId);

        verify(userSkillRepository).deleteById(10L);
        verify(redisTemplate).delete("users:1:matches");
    }

    @Test
    void removeSkillFromUser_shouldThrowNotFound_whenSkillDoesNotExist() {

        long userId = 1L;
        long skillId = 2L;

        when(userSkillRepository.findByUserIdAndSkillId(userId, skillId))
                .thenReturn(null);

        assertThrows(
                ResourceNotFoundException.class,
                () -> userSkillService.removeSkillFromUser(userId, skillId)
        );

        verify(userSkillRepository, never()).deleteById(anyLong());
        verify(redisTemplate, never()).delete(anyString());
    }
}