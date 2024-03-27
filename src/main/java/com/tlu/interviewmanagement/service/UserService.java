package com.tlu.interviewmanagement.service;

import com.tlu.interviewmanagement.entities.Users;
import com.tlu.interviewmanagement.web.request.PasswordRequest;
import com.tlu.interviewmanagement.web.request.SearchRequest;
import com.tlu.interviewmanagement.web.request.UserRequest;
import com.tlu.interviewmanagement.web.response.Interviewer;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    Users findUserByAccount_Email(String email);
    List<Interviewer> findInterviewerByDate(LocalDate date);
    List<Users> findUserByRoleInterviewAndRecruiter();
    List<Users> findUserByRoleManager();
    List<Users> findUserByRoleInterview();
    List<Users> findUserByRoleRecruiter();
    List<Users> findUserByRoleRecruiterAndManager();

    Page<Users> findAllUser(SearchRequest searchRequest);
    Users findUserById(Long id);
    void deleteUserById(Long id);
    Users findUserByAccountId(String email);
    Users saveUser(UserRequest userRequest) throws MessagingException;
    Users updateUser(UserRequest userRequest);
    Users changePassword(PasswordRequest passwordRequest, String email);

}
