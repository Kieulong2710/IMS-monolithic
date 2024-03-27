package com.tlu.interviewmanagement.service.impl;

import com.tlu.interviewmanagement.entities.Account;
import com.tlu.interviewmanagement.enums.EMessageUser;
import com.tlu.interviewmanagement.repository.AccountRepository;
import com.tlu.interviewmanagement.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public Account findAccountByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(EMessageUser.DONT_USER.getValue()));
    }

    @Override
    public boolean existedAccountByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }
}
