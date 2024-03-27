package com.tlu.interviewmanagement.repository;

import com.tlu.interviewmanagement.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findAllByNameIn(List<String> skillNames);
}
