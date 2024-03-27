package com.tlu.interviewmanagement.service;

import com.tlu.interviewmanagement.entities.ResultInterview;
import com.tlu.interviewmanagement.web.request.ResultRequest;

import java.util.List;

public interface ResultService {
    ResultInterview updateResult(ResultRequest resultRequest);
    ResultInterview findById(Long id);
    List<ResultInterview> findByResultPass();
    ResultInterview findResultInterviewByCandidate_Id(Long id);
}
