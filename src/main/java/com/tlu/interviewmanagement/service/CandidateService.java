package com.tlu.interviewmanagement.service;

import com.tlu.interviewmanagement.entities.Candidate;
import com.tlu.interviewmanagement.enums.EStatus;
import com.tlu.interviewmanagement.web.request.CandidateRequest;
import com.tlu.interviewmanagement.web.request.SearchRequest;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface CandidateService {
    Candidate saveCandidate(CandidateRequest candidateRequest) throws GeneralSecurityException, IOException;
    List<Candidate> findCandidateByStatus(EStatus status);
    Candidate findCandidateById(Long id);
    List<Candidate> findCandidateByJobId(Long id);

    Page<Candidate> findAllCandidate(SearchRequest searchRequest);

    Candidate updateCandidate(CandidateRequest candidateRequest) throws IOException, GeneralSecurityException;

    void deleteCandidate(Long id);


}
