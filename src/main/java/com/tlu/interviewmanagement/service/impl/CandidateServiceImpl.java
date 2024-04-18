package com.tlu.interviewmanagement.service.impl;

import com.tlu.interviewmanagement.entities.Candidate;
import com.tlu.interviewmanagement.entities.Job;
import com.tlu.interviewmanagement.enums.EMessageCandidate;
import com.tlu.interviewmanagement.enums.EStatus;
import com.tlu.interviewmanagement.repository.CandidateRepository;
import com.tlu.interviewmanagement.repository.JobRepository;
import com.tlu.interviewmanagement.repository.OfferRepository;
import com.tlu.interviewmanagement.repository.ResultInterviewRepository;
import com.tlu.interviewmanagement.service.CandidateService;
import com.tlu.interviewmanagement.utils.GoogleApiUtil;
import com.tlu.interviewmanagement.web.request.CandidateRequest;
import com.tlu.interviewmanagement.web.request.SearchRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final GoogleApiUtil googleApiUtil;
    private final ModelMapper modelMapper;
    private final OfferRepository offerRepository;
    private final ResultInterviewRepository resultInterviewRepository;
    private final JobRepository jobRepository;
    @Override
    @Transactional
    public Candidate saveCandidate(CandidateRequest candidateRequest) throws GeneralSecurityException, IOException {
        Candidate candidate = getCandidate(new Candidate(), candidateRequest);
        if(candidateRepository.existsAllByEmailAndJob_Id(candidate.getEmail(),candidateRequest.getJobId())){
            return null;
        }
        Job job = jobRepository.findById(candidateRequest.getJobId()).orElseThrow();
        job.setApply(job.getApply() + 1L);
        candidate.setJob(job);
        return candidateRepository.save(candidate);
    }

    @Override
    public List<Candidate> findCandidateByStatus(EStatus status) {
        return candidateRepository.findAllByStatus(status);
    }

    @Override
    public Candidate findCandidateById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(EMessageCandidate.DONT_CANDIDATE.getValue()));
    }

    @Override
    public List<Candidate> findCandidateByJobId(Long id) {
        return candidateRepository.findCandidateByJob_IdAndStatus(id,EStatus.OPEN);
    }

    @Override
    public Page<Candidate> findAllCandidate(SearchRequest searchRequest) {
        Pageable pageable = PageRequest.of(searchRequest.getPageNumber() - 1, SearchRequest.PAGE_SIZE);
        if (searchRequest.getStatus() == null) {
            return candidateRepository.findAll(searchRequest.getParam(), pageable);
        }
        return candidateRepository.findAll(searchRequest.getParam(), searchRequest.getStatus(), pageable);
    }

    @Override
    @Transactional
    public Candidate updateCandidate(CandidateRequest candidateRequest) throws IOException, GeneralSecurityException {
        Candidate candidate = findCandidateById(candidateRequest.getId());
        getCandidate(candidate, candidateRequest);
        return candidateRepository.save(candidate);
    }

    @Override
    @Transactional
    public void deleteCandidate(Long id) {
        Candidate candidate = findCandidateById(id);
        Job job =candidate.getJob();
        job.setApply(job.getApply()-1L);
        offerRepository.deleteByCandidateId(id);
        resultInterviewRepository.deleteByCandidateId(id);
        candidateRepository.deleteById(id);
    }




    private Candidate getCandidate(Candidate candidate, CandidateRequest candidateRequest) throws GeneralSecurityException, IOException {
        modelMapper.map(candidateRequest, candidate);
        if (candidate.getStatus() == null) {
            candidate.setStatus(EStatus.OPEN);
        }
        String cvName = candidateRequest.getCv().getOriginalFilename();
        if (cvName != null &&
                !cvName.isEmpty()) {
            candidate.setCv(cvName);
            String cvId = googleApiUtil.uploadCv(candidateRequest.getCv());
            candidate.setCvId(cvId);
        }
        return candidate;
    }
}
