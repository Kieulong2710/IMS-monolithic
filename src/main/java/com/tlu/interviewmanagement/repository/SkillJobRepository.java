package com.tlu.interviewmanagement.repository;

import com.tlu.interviewmanagement.entities.KeySkillJob;
import com.tlu.interviewmanagement.entities.SkillJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SkillJobRepository extends JpaRepository<SkillJob, KeySkillJob> {
    @Modifying
    @Query("DELETE SkillJob WHERE job.id = ?1")
    void deleteSkillJob(Long id);

}
