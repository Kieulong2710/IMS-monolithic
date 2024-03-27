package com.tlu.interviewmanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(KeyInterviewerSchedule.class)
public class InterviewerSchedule implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "interview_id")
    private Users interviewer;

    @Id
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private InterviewSchedule schedule;

}
