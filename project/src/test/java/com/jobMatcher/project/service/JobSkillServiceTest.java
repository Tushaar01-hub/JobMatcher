package com.jobMatcher.project.service;

import com.jobMatcher.project.dtos.JobSkillResponseDTO;
import com.jobMatcher.project.entity.Job;
import com.jobMatcher.project.entity.JobSkill;
import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.exception.DuplicateResourceException;
import com.jobMatcher.project.exception.ResourceNotFoundException;
import com.jobMatcher.project.repository.JobRepository;
import com.jobMatcher.project.repository.JobSkillRepository;
import com.jobMatcher.project.repository.SkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobSkillServiceTest {

    @Mock
    private JobSkillRepository jobSkillRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private SkillRepository skillRepository;

    private JobSkillService jobSkillService;

    @BeforeEach
    void setUp() {
        jobSkillService = new JobSkillService(
                jobSkillRepository,
                jobRepository,
                skillRepository
        );
    }

    @Test
    void addSkillToJob_shouldAddSkillSuccessfully() {

        long jobId = 1L;
        long skillId = 2L;

        Job job = new Job();
        job.setId(jobId);

        Skill skill = new Skill();
        skill.setId(skillId);
        skill.setName("Java");

        JobSkill savedJobSkill = new JobSkill();
        savedJobSkill.setId(10L);
        savedJobSkill.setJob(job);
        savedJobSkill.setSkill(skill);

        when(jobSkillRepository.existsByJobIdAndSkillId(jobId, skillId))
                .thenReturn(false);

        when(jobRepository.findById(jobId))
                .thenReturn(Optional.of(job));

        when(skillRepository.findById(skillId))
                .thenReturn(Optional.of(skill));

        when(jobSkillRepository.save(any(JobSkill.class)))
                .thenReturn(savedJobSkill);

        JobSkillResponseDTO result =
                jobSkillService.addSkillToJob(jobId, skillId);

        assertEquals(jobId, result.getJobId());
        assertEquals(skillId, result.getSkillId());
        assertEquals("Java", result.getSkillName());

        verify(jobSkillRepository).save(any(JobSkill.class));
    }

    @Test
    void addSkillToJob_shouldThrowDuplicate_whenSkillAlreadyExists() {

        long jobId = 1L;
        long skillId = 2L;

        when(jobSkillRepository.existsByJobIdAndSkillId(jobId, skillId))
                .thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> jobSkillService.addSkillToJob(jobId, skillId)
        );

        verify(jobRepository, never()).findById(jobId);
        verify(skillRepository, never()).findById(skillId);
        verify(jobSkillRepository, never()).save(any(JobSkill.class));
    }

    @Test
    void addSkillToJob_shouldThrowNotFound_whenJobDoesNotExist() {

        long jobId = 1L;
        long skillId = 2L;

        when(jobSkillRepository.existsByJobIdAndSkillId(jobId, skillId))
                .thenReturn(false);

        when(jobRepository.findById(jobId))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> jobSkillService.addSkillToJob(jobId, skillId)
        );

        verify(skillRepository, never()).findById(skillId);
        verify(jobSkillRepository, never()).save(any(JobSkill.class));
    }

    @Test
    void getJobSkills_shouldReturnJobSkills() {

        long jobId = 1L;

        Job job = new Job();
        job.setId(jobId);

        Skill skill = new Skill();
        skill.setId(2L);
        skill.setName("Spring Boot");

        JobSkill jobSkill = new JobSkill();
        jobSkill.setJob(job);
        jobSkill.setSkill(skill);

        when(jobSkillRepository.findAllByJobId(jobId))
                .thenReturn(List.of(jobSkill));

        List<JobSkillResponseDTO> result =
                jobSkillService.getJobSkills(jobId);

        assertEquals(1, result.size());
        assertEquals(jobId, result.get(0).getJobId());
        assertEquals(2L, result.get(0).getSkillId());
        assertEquals("Spring Boot", result.get(0).getSkillName());

        verify(jobSkillRepository).findAllByJobId(jobId);
    }

    @Test
    void removeSkillFromJob_shouldRemoveSkillSuccessfully() {

        long jobId = 1L;
        long skillId = 2L;

        JobSkill jobSkill = new JobSkill();
        jobSkill.setId(10L);

        when(jobSkillRepository.findByJobIdAndSkillId(jobId, skillId))
                .thenReturn(jobSkill);

        jobSkillService.removeSkillFromJob(jobId, skillId);

        verify(jobSkillRepository).delete(jobSkill);
    }

    @Test
    void removeSkillFromJob_shouldThrowNotFound_whenSkillDoesNotExist() {

        long jobId = 1L;
        long skillId = 2L;

        when(jobSkillRepository.findByJobIdAndSkillId(jobId, skillId))
                .thenReturn(null);

        assertThrows(
                ResourceNotFoundException.class,
                () -> jobSkillService.removeSkillFromJob(jobId, skillId)
        );

        verify(jobSkillRepository, never()).delete(any(JobSkill.class));
    }
}