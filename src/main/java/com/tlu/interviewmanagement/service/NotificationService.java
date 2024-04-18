package com.tlu.interviewmanagement.service;

import com.tlu.interviewmanagement.entities.Candidate;
import com.tlu.interviewmanagement.entities.Notification;
import com.tlu.interviewmanagement.web.request.InterviewRequest;

import java.util.List;

public interface NotificationService {
    void NotificationAddCandidate(Candidate candidate);

    List<Notification> getNotifications(Long userId);

    void checked(Long id);

    void NotificationAddInterviewSchedule(Long id, InterviewRequest interviewRequest);
}
