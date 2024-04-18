package com.tlu.interviewmanagement.scheduling;


import com.tlu.interviewmanagement.entities.InterviewSchedule;
import com.tlu.interviewmanagement.entities.InterviewerSchedule;
import com.tlu.interviewmanagement.repository.InterviewScheduleRepository;
import com.tlu.interviewmanagement.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class AutoUpdate {
    private final InterviewScheduleRepository interviewScheduleRepository;
    private final EmailService emailService;


    @Scheduled(cron = "01 00 00 * * *")
    public void updateStatusInterviewerSchedule() {
//        LocalDate localDate = LocalDate.now();
//        List<InterviewSchedule> interviewSchedules = interviewScheduleRepository.findAllBySchedule(localDate);
//        interviewSchedules.forEach(x -> {
//            x.setStatus(EStatus.IN_PROGRESS);
//            x.getResultInterviews().getCandidate().setStatus(EStatus.IN_PROGRESS);
//        });
//        interviewScheduleRepository.saveAll(interviewSchedules);
    }

    @Scheduled(cron = "01 00 08 * * *")
    public void sendMailRemind() throws MessagingException {
//        LocalDate localDate = LocalDate.now();
//        List<InterviewSchedule> interviewSchedules = interviewScheduleRepository.findAllBySchedule(localDate);
//        Set<InterviewerSchedule> interviewers = new HashSet<>();
//        interviewSchedules.forEach(x -> interviewers.addAll(x.getInterviewer()));
//        emailService.sendMailInterviewScheduleToInterviewer(interviewers);
    }
}
