package com.tlu.interviewmanagement.repository;

import com.tlu.interviewmanagement.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> getNotificationsByUserIdOrderByIdDesc(Long id);
}
