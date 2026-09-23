package com.jobMatcher.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class UserSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "skillid",unique = true)
    private Skill skill;
    @ManyToOne
    @JoinColumn(name = "userid",unique = true)
    private User user;
}
