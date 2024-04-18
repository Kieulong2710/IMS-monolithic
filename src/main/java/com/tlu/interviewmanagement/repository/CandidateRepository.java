package com.tlu.interviewmanagement.repository;

import com.tlu.interviewmanagement.entities.Candidate;
import com.tlu.interviewmanagement.enums.EStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {


    @Query("select c from Candidate c where " +
            "c.fullName LIKE %?1% " +
            " AND c.status = ?2 " +
            "order by c.id desc ")
    Page<Candidate> findAll(String nameKeyword, EStatus status, Pageable pageable);

    @Query("select c from Candidate c where " +
            "c.fullName LIKE %?1% " +
            "order by c.id desc ")
    Page<Candidate> findAll(String nameKeyword, Pageable pageable);

    List<Candidate> findAllByStatus(EStatus status);
    @Query("select c from Candidate c " +
            "where c.job.id = ?1 " +
            "and c.status = 'OPEN' " +
            "order by c.id asc ")
    List<Candidate> findAllByJob_IdOrOrderByIdAsc(Long id, Long limit);

    boolean existsAllByEmailAndJob_Id(String email,Long jobId);


    List<Candidate> findCandidateByJob_IdAndStatus(Long id,EStatus status);

    @Modifying
    @Query("delete Candidate where id = ?1")
    void deleteById(Long id);
}
