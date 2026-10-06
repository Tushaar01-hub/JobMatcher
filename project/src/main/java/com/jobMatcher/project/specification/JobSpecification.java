package com.jobMatcher.project.specification;

import com.jobMatcher.project.entity.Job;
import com.jobMatcher.project.entity.JobSkill;
import com.jobMatcher.project.enums.ExperienceLevel;
import org.springframework.data.jpa.domain.Specification;

public class JobSpecification {
    public static Specification<Job> hasTitle(String title){
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")),
                    "%"+title.toLowerCase()+"%"
            );
        });
    }
    public static Specification<Job> hasCompany(String company){
        return (((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("company")),
                    company.toLowerCase()
            );
        }));
    }
    public static Specification<Job> hasMinSalary(Long minSalary){
        return (((root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get("salary"),minSalary
            );
        }));
    }
    public static  Specification<Job> hasExpLvl(ExperienceLevel explvl){
        return (((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(
                    root.get("experienceLevel"),
                    explvl
            );
        }));
    }
    public static Specification<Job> hasSkill(String skillName){
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(
                    criteriaBuilder.lower(root.join("jobSkills").join("skill").get("name")),
                    skillName.toLowerCase()
            );
        };
    }
}
