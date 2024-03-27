package com.tlu.interviewmanagement.repository;

import com.tlu.interviewmanagement.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    boolean    existsByEmailIn(List<String> emails);
    boolean existsByEmail(String email);
}
