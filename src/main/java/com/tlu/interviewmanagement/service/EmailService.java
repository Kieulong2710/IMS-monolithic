package com.tlu.interviewmanagement.service;

import com.tlu.interviewmanagement.entities.Account;
import com.tlu.interviewmanagement.entities.EmailDetail;
import com.tlu.interviewmanagement.entities.InterviewSchedule;
import com.tlu.interviewmanagement.entities.Offer;
import jakarta.mail.MessagingException;

import java.util.Collection;

public interface EmailService {
    void sendMailHtml(EmailDetail emailDetail   ) throws MessagingException;
    void sendMailNotificationInterviewSchedule(Collection<String> email,String subject,InterviewSchedule interviewSchedule) throws MessagingException;
    void sendMailCancelInterviewSchedule(Collection<String> email,String subject,InterviewSchedule interviewSchedule) throws MessagingException;
    void sendMailChangeInterviewSchedule(Collection<String> email,String subject,InterviewSchedule interviewSchedule) throws MessagingException;
    void sendMailToUser(Account account,String password) throws MessagingException;
    void sendMailNotifyOnBoard(Collection<String> email, String subject, Offer offer) throws MessagingException;
}