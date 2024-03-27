package com.tlu.interviewmanagement.service;

import com.tlu.interviewmanagement.entities.InterviewSchedule;
import com.tlu.interviewmanagement.web.request.InterviewRequest;
import com.tlu.interviewmanagement.web.request.SearchRequest;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;

public interface InterviewService {
    InterviewSchedule saveInterviewSchedule(InterviewRequest interviewRequest) throws MessagingException;
    Page<InterviewSchedule> findAllInterviewSchedule(SearchRequest searchRequest);
    InterviewSchedule findInterviewScheduleById(Long id);

    InterviewSchedule updateInterviewSchedule(InterviewRequest interviewRequest) throws MessagingException;
    void deleteInterviewSchedule(Long id) throws MessagingException;
}
