package com.tlu.interviewmanagement.config;

import com.tlu.interviewmanagement.entities.CustomUserDetail;
import com.tlu.interviewmanagement.service.AccountService;
import com.tlu.interviewmanagement.service.impl.InterviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final AccountService accountService;
    Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> new CustomUserDetail(accountService.findAccountByEmail(email));
    }

    @Bean
    public AuthenticationProvider provider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}