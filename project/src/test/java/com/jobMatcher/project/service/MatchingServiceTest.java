package com.jobMatcher.project.service;

import com.jobMatcher.project.entity.Job;
import com.jobMatcher.project.entity.JobSkill;
import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.entity.User;
import com.jobMatcher.project.entity.UserSkill;
import com.jobMatcher.project.repository.JobRepository;
import com.jobMatcher.project.repository.JobSkillRepository;
import com.jobMatcher.project.repository.UserRepository;
import com.jobMatcher.project.repository.UserSkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchingServiceTest {
    @Mock
    private RedisTemplate<String, Object> redisTemplate;
    @Mock
    private ValueOperations<String, Object> valueOperations;
    @Mock
    private UserRepository userRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private UserSkillRepository userSkillRepository;

    @Mock
    private JobSkillRepository jobSkillRepository;

    @InjectMocks
    private MatchingService matchingService;


    @Test
    void getUserMatchingJobs_shouldCalculateMatchCorrectly() {

        // USER
        User user = new User();
        user.setId(1L);

        Skill java = new Skill();
        java.setId(1L);
        java.setName("Java");

        Skill spring = new Skill();
        spring.setId(2L);
        spring.setName("Spring Boot");

        UserSkill userJava = new UserSkill();
        userJava.setUser(user);
        userJava.setSkill(java);

        UserSkill userSpring = new UserSkill();
        userSpring.setUser(user);
        userSpring.setSkill(spring);


        // JOB
        Job job = new Job();
        job.setId(10L);
        job.setTitle("Java Backend Developer");
        job.setCompany("Google");

        Skill sql = new Skill();
        sql.setId(3L);
        sql.setName("SQL");

        JobSkill jobJava = new JobSkill();
        jobJava.setJob(job);
        jobJava.setSkill(java);

        JobSkill jobSpring = new JobSkill();
        jobSpring.setJob(job);
        jobSpring.setSkill(spring);

        JobSkill jobSql = new JobSkill();
        jobSql.setJob(job);
        jobSql.setSkill(sql);


        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userSkillRepository.findAllByUserId(1L))
                .thenReturn(List.of(userJava, userSpring));

        when(jobRepository.findAll())
                .thenReturn(List.of(job));

        when(jobSkillRepository.findAllByJobIdIn(List.of(10L)))
                .thenReturn(List.of(jobJava, jobSpring, jobSql));

        when(redisTemplate.opsForValue())
                .thenReturn(valueOperations);

        when(valueOperations.get(anyString()))
                .thenReturn(null);
        var result = matchingService.getUserMatchingJob(1L);


        assertEquals(1, result.size());

        assertEquals(10L, result.get(0).getJobId());

        assertEquals(2, result.get(0).getMatchedSkills().size());

        assertEquals(1, result.get(0).getMissingSkills().size());

        assertEquals(66.67, result.get(0).getMatchPercentage());

        verify(userRepository).findById(1L);
        verify(userSkillRepository).findAllByUserId(1L);
        verify(jobRepository).findAll();
        verify(jobSkillRepository).findAllByJobIdIn(List.of(10L));
    }
    @Test
    void getUserMatchingJobs_shouldSortByMatchPercentageDescending() {

        // USER
        User user = new User();
        user.setId(1L);

        Skill java = new Skill();
        java.setId(1L);
        java.setName("Java");

        Skill spring = new Skill();
        spring.setId(2L);
        spring.setName("Spring Boot");

        UserSkill userJava = new UserSkill();
        userJava.setUser(user);
        userJava.setSkill(java);

        UserSkill userSpring = new UserSkill();
        userSpring.setUser(user);
        userSpring.setSkill(spring);


        // JOB 1 -> 1/2 skills matched = 50%
        Job job1 = new Job();
        job1.setId(1L);
        job1.setTitle("Java Developer");

        JobSkill job1Java = new JobSkill();
        job1Java.setJob(job1);
        job1Java.setSkill(java);

        Skill sql = new Skill();
        sql.setId(3L);
        sql.setName("SQL");

        JobSkill job1Sql = new JobSkill();
        job1Sql.setJob(job1);
        job1Sql.setSkill(sql);


        // JOB 2 -> 2/2 skills matched = 100%
        Job job2 = new Job();
        job2.setId(2L);
        job2.setTitle("Spring Developer");

        JobSkill job2Java = new JobSkill();
        job2Java.setJob(job2);
        job2Java.setSkill(java);

        JobSkill job2Spring = new JobSkill();
        job2Spring.setJob(job2);
        job2Spring.setSkill(spring);


        // MOCKS
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userSkillRepository.findAllByUserId(1L))
                .thenReturn(List.of(userJava, userSpring));

        when(jobRepository.findAll())
                .thenReturn(List.of(job1, job2));

        when(jobSkillRepository.findAllByJobIdIn(List.of(1L, 2L)))
                .thenReturn(List.of(
                        job1Java,
                        job1Sql,
                        job2Java,
                        job2Spring
                ));
        when(redisTemplate.opsForValue())
                .thenReturn(valueOperations);

        when(valueOperations.get(anyString()))
                .thenReturn(null);


        // ACT
        var result = matchingService.getUserMatchingJob(1L);


        // ASSERT
        assertEquals(2, result.size());

        // Higher match should come first
        assertEquals(2L, result.get(0).getJobId());
        assertEquals(100.0, result.get(0).getMatchPercentage());

        assertEquals(1L, result.get(1).getJobId());
        assertEquals(50.0, result.get(1).getMatchPercentage());
    }
}