package com.tlu.interviewmanagement.service.impl;

import com.tlu.interviewmanagement.entities.*;
import com.tlu.interviewmanagement.enums.ERole;
import com.tlu.interviewmanagement.repository.JobRepository;
import com.tlu.interviewmanagement.repository.NotificationRepository;
import com.tlu.interviewmanagement.repository.UserRepository;
import com.tlu.interviewmanagement.service.NotificationService;
import com.tlu.interviewmanagement.web.request.InterviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;


    @Override
    @Transactional
    public void NotificationAddCandidate(Candidate candidate) {
        Job job = candidate.getJob();
        String comment = "<strong>" + candidate.getFullName() + "</strong>  đã nạp hồ sơ vào việc làm <strong>" + job.getTitle() + "</strong>";
        String link = "/admin/candidate/" + candidate.getId();
        List<Users> users = userRepository
                .findUsersByAccount_RoleIn(List.of(ERole.ROLE_MANAGER, ERole.ROLE_RECRUITER));
        List<Notification> notifications = users.stream()
                .map(u -> Notification.builder()
                        .comment(comment)
                        .link(link)
                        .userId(u.getId())
                        .checked(false)
                        .build()).toList();
        notificationRepository.saveAll(notifications);
    }

    @Override
    public List<Notification> getNotifications(Long userId) {
        return notificationRepository.getNotificationsByUserIdOrderByIdDesc(userId);
    }

    @Override
    @Transactional
    public void checked(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow();
        notification.setChecked(true);
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void NotificationAddInterviewSchedule(Long id, InterviewRequest interviewRequest) {
        String comment = "Bạn đã được thêm vào làm ngưởi phỏng vấn của buổi phỏng vấn: <strong>" + interviewRequest.getTitle() + "</strong>";
        String link = "/admin/interview/" + id;
        List<Notification> notifications = new ArrayList<>();
        for (Long u : interviewRequest.getInterviewId()) {
            notifications.add(Notification.builder()
                    .comment(comment)
                    .link(link)
                    .userId(u)
                    .checked(false)
                    .build());
        }
        notificationRepository.saveAll(notifications);
    }


}
