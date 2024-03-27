package com.tlu.interviewmanagement.service;

import com.tlu.interviewmanagement.entities.Account;

public interface AccountService {
    Account findAccountByEmail(String email);
    boolean existedAccountByEmail(String email);
}
