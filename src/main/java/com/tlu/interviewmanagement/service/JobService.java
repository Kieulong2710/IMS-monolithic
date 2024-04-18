package com.tlu.interviewmanagement.service;

import com.tlu.interviewmanagement.entities.Job;
import com.tlu.interviewmanagement.web.request.JobRequest;
import com.tlu.interviewmanagement.web.request.JobSearch;
import com.tlu.interviewmanagement.web.request.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface JobService {
    Job saveJob(JobRequest jobRequest);

    Page<Job> findAllJob(SearchRequest searchRequest);
    Page<Job> findAllJob(JobSearch jobSearch);

    int deleteJobById(Long id);

    Job findJobById(Long id);
    List<Job> findJobByStatusOpen();
    List<Job> findJobByStatusOpenAndApply();

    Job updateJob(Job job, JobRequest jobRequest);

    List<Job> saveAllJobs(MultipartFile fileImport);

    Page<Job> findNewJob();
}
