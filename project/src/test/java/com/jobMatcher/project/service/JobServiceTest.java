package com.jobMatcher.project.service;

import com.jobMatcher.project.dtos.JobRequestDTO;
import com.jobMatcher.project.dtos.JobResponseDTO;
import com.jobMatcher.project.entity.Job;
import com.jobMatcher.project.entity.JobSkill;
import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.exception.ResourceNotFoundException;
import com.jobMatcher.project.repository.JobRepository;
import com.jobMatcher.project.repository.JobSkillRepository;
import com.jobMatcher.project.repository.SkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import com.jobMatcher.project.enums.ExperienceLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private JobSkillRepository jobSkillRepository;

    @Mock
    private SkillRepository skillRepository;

    private JobService jobService;

    @BeforeEach
    void setUp() {

        jobService = new JobService(
                modelMapper,
                jobRepository,
                jobSkillRepository,
                skillRepository
        );
    }

    @Test
    void deleteJob_shouldThrowNotFound_whenJobDoesNotExist() {

        long jobId = 1L;

        when(jobRepository.findById(jobId))
                .thenReturn(java.util.Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> jobService.deleteJob(jobId)
        );

        verify(jobSkillRepository, never()).findAllByJobId(jobId);
        verify(jobSkillRepository, never()).deleteAll(anyList());
        verify(jobRepository, never()).delete(any(Job.class));
    }

    @Test
    void deleteJob_shouldDeleteJobAndItsSkills_whenJobExists() {

        long jobId = 1L;

        Job job = new Job();
        job.setId(jobId);

        JobSkill jobSkill = new JobSkill();

        when(jobRepository.findById(jobId))
                .thenReturn(java.util.Optional.of(job));

        when(jobSkillRepository.findAllByJobId(jobId))
                .thenReturn(List.of(jobSkill));

        // ACT
        jobService.deleteJob(jobId);

        // VERIFY
        verify(jobSkillRepository).findAllByJobId(jobId);
        verify(jobSkillRepository).deleteAll(List.of(jobSkill));
        verify(jobRepository).delete(job);
    }
    @Test
    void createJob_shouldThrowNotFound_whenSkillDoesNotExist() {

        JobRequestDTO request = new JobRequestDTO();
        request.setSkillIds(List.of(1L, 2L));

        Skill skill1 = new Skill();
        skill1.setId(1L);
        skill1.setName("Java");

        when(skillRepository.findById(1L))
                .thenReturn(java.util.Optional.of(skill1));

        when(skillRepository.findById(2L))
                .thenReturn(java.util.Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> jobService.createJob(request)
        );

        verify(jobRepository, never()).save(any(Job.class));
        verify(jobSkillRepository, never()).save(any(JobSkill.class));
    }
    @Test
    void getJobsByFilter_shouldReturnMatchingJobs() {

        Job job = new Job();
        job.setId(1L);
        job.setTitle("Java Developer");
        job.setCompany("Google");

        Pageable pageable = PageRequest.of(0, 5);

        Page<Job> jobPage = new PageImpl<>(
                List.of(job),
                pageable,
                1
        );

        when(jobRepository.findAll(
                any(org.springframework.data.jpa.domain.Specification.class),
                eq(pageable)
        )).thenReturn(jobPage);

        when(jobSkillRepository.findAllByJobIdIn(List.of(1L)))
                .thenReturn(List.of());

        Page<JobResponseDTO> result = jobService.getJobsByFilter(
                "Java",
                ExperienceLevel.FRESHER,
                50000L,
                "Google",
                null,
                pageable
        );

        assertEquals(1, result.getTotalElements());
        assertEquals("Java Developer", result.getContent().get(0).getTitle());
        assertEquals("Google", result.getContent().get(0).getCompany());

        verify(jobRepository).findAll(
                any(org.springframework.data.jpa.domain.Specification.class),
                eq(pageable)
        );

        verify(jobSkillRepository).findAllByJobIdIn(List.of(1L));
    }
    @Test
    void getJobsByFilter_shouldReturnJobs_whenNoFiltersProvided() {

        Job job = new Job();
        job.setId(1L);
        job.setTitle("Backend Developer");

        Pageable pageable = PageRequest.of(0, 5);

        Page<Job> jobPage = new PageImpl<>(
                List.of(job),
                pageable,
                1
        );

        when(jobRepository.findAll(
                any(org.springframework.data.jpa.domain.Specification.class),
                eq(pageable)
        )).thenReturn(jobPage);

        when(jobSkillRepository.findAllByJobIdIn(List.of(1L)))
                .thenReturn(List.of());

        Page<JobResponseDTO> result = jobService.getJobsByFilter(
                null,
                null,
                null,
                null,
                null,
                pageable
        );

        assertEquals(1, result.getTotalElements());
        assertEquals(
                "Backend Developer",
                result.getContent().get(0).getTitle()
        );

        verify(jobRepository).findAll(
                any(org.springframework.data.jpa.domain.Specification.class),
                eq(pageable)
        );
    }
}