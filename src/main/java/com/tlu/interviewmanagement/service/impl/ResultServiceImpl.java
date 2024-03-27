package com.tlu.interviewmanagement.service.impl;

import com.tlu.interviewmanagement.entities.ResultInterview;
import com.tlu.interviewmanagement.enums.EStatus;
import com.tlu.interviewmanagement.repository.ResultInterviewRepository;
import com.tlu.interviewmanagement.service.ResultService;
import com.tlu.interviewmanagement.web.request.ResultRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {
    private final ResultInterviewRepository resultInterviewRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResultInterview updateResult(ResultRequest resultRequest) {
        ResultInterview resultInterview = findById(resultRequest.getId());
        resultInterviewRepository.save(getResultInterview(resultInterview, resultRequest));
        return resultInterview;
    }

    @Override
    public ResultInterview findById(Long id) {
        return resultInterviewRepository.findById(id).orElseThrow();
    }

    @Override
    public List<ResultInterview> findByResultPass() {
        return resultInterviewRepository.findByResult(EStatus.PASS);
    }

    @Override
    public ResultInterview findResultInterviewByCandidate_Id(Long id) {
        return resultInterviewRepository.findByCandidate_Id(id).orElseThrow();
    }

    private ResultInterview getResultInterview(ResultInterview resultInterview, ResultRequest resultRequest) {
        modelMapper.map(resultRequest, resultInterview);
        resultInterview.getCandidate().setStatus(getStatus(resultRequest.getResult()));
        return resultInterview;
    }

    private EStatus getStatus(EStatus status) {
        if (status == EStatus.PASS) {
            return EStatus.PASSED_INTERVIEW;
        } else if (status == EStatus.FAIL) {
            return EStatus.FAILED_INTERVIEW;
        } else {
            return EStatus.CANCELED_INTERVIEW;
        }
    }
}
