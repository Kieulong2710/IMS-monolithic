package com.tlu.interviewmanagement.service;

import com.tlu.interviewmanagement.entities.Account;
import com.tlu.interviewmanagement.entities.EmailDetail;
import com.tlu.interviewmanagement.entities.InterviewSchedule;
import jakarta.mail.MessagingException;

import java.util.Collection;

public interface EmailService {
    void sendMailSimple(EmailDetail emailDetail) throws MessagingException;
    void sendMailHtml(EmailDetail emailDetail   ) throws MessagingException;
    void sendMailNotificationInterviewSchedule(Collection<String> email, String subject, InterviewSchedule interviewSchedule) throws MessagingException;
    void sendMailCancelInterviewSchedule(Collection<String> email,String subject,InterviewSchedule interviewSchedule) throws MessagingException;
    void sendMailChangeInterviewSchedule(Collection<String> email,String subject,InterviewSchedule interviewSchedule) throws MessagingException;
    void sendMailToUser(Account account, String password) throws MessagingException;

}
