package com.tlu.interviewmanagement.repository;

import com.tlu.interviewmanagement.entities.ResultInterview;
import com.tlu.interviewmanagement.enums.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResultInterviewRepository extends JpaRepository<ResultInterview, Long> {

    Optional<ResultInterview> findByCandidate_Id(Long id);
    Optional<ResultInterview> findByInterviewSchedule_Id(Long id);

    @Query("SELECT ri " +
            "FROM ResultInterview ri " +
            "WHERE ri.result = ?1 AND ri.candidate.status = 'PASSED_INTERVIEW'")
    List<ResultInterview> findByResult(EStatus status);
    @Modifying
    @Query("DELETE ResultInterview where interviewSchedule.id = ?1")
    void deleteByInterviewSchedule_Id(Long id);

    @Query(value = "SELECT COUNT(*) FROM result_interview " +
            "JOIN interviewmanagement.candidate c on c.candidate_id = result_interview.candidate_id " +
            "JOIN job j on c.job_id = j.id " +
            "where j.id = :jobId ", nativeQuery = true)
    long countAllCandidate(@Param("jobId") Long jobId);

    @Modifying
    @Query("DELETE ResultInterview where candidate.id = ?1")
    void deleteByCandidateId(Long id);
}
