package com.jobMatcher.project.service;

import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.exception.ResourceConflictException;
import com.jobMatcher.project.exception.ResourceNotFoundException;
import com.jobMatcher.project.repository.JobSkillRepository;
import com.jobMatcher.project.repository.SkillRepository;
import com.jobMatcher.project.repository.UserSkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SkillServiceTest {

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserSkillRepository userSkillRepository;

    @Mock
    private JobSkillRepository jobSkillRepository;

    private SkillService skillService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        skillService = new SkillService(
                skillRepository,
                modelMapper,
                userSkillRepository,
                jobSkillRepository
        );
    }

    @Test
    void deleteSkill_shouldThrowNotFound_whenSkillDoesNotExist() {

        long skillId = 1L;

        when(skillRepository.existsById(skillId))
                .thenReturn(false);

        assertThrows(
                ResourceNotFoundException.class,
                () -> skillService.deleteSkill(skillId)
        );

        verify(skillRepository, never()).deleteById(skillId);
    }

    @Test
    void deleteSkill_shouldThrowConflict_whenSkillIsAssignedToUser() {

        long skillId = 1L;

        when(skillRepository.existsById(skillId))
                .thenReturn(true);

        when(userSkillRepository.existsBySkillId(skillId))
                .thenReturn(true);

        assertThrows(
                ResourceConflictException.class,
                () -> skillService.deleteSkill(skillId)
        );

        verify(jobSkillRepository, never()).existsBySkillId(skillId);
        verify(skillRepository, never()).deleteById(skillId);
    }

    @Test
    void deleteSkill_shouldThrowConflict_whenSkillIsAssignedToJob() {

        long skillId = 1L;

        when(skillRepository.existsById(skillId))
                .thenReturn(true);

        when(userSkillRepository.existsBySkillId(skillId))
                .thenReturn(false);

        when(jobSkillRepository.existsBySkillId(skillId))
                .thenReturn(true);

        assertThrows(
                ResourceConflictException.class,
                () -> skillService.deleteSkill(skillId)
        );

        verify(skillRepository, never()).deleteById(skillId);
    }

    @Test
    void deleteSkill_shouldDeleteSkill_whenSkillIsUnused() {

        long skillId = 1L;

        when(skillRepository.existsById(skillId))
                .thenReturn(true);

        when(userSkillRepository.existsBySkillId(skillId))
                .thenReturn(false);

        when(jobSkillRepository.existsBySkillId(skillId))
                .thenReturn(false);

        assertDoesNotThrow(
                () -> skillService.deleteSkill(skillId)
        );

        verify(skillRepository).deleteById(skillId);
    }
}