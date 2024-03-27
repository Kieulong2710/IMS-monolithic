package com.tlu.interviewmanagement.service.impl;

import com.tlu.interviewmanagement.entities.Level;
import com.tlu.interviewmanagement.repository.LevelRepository;
import com.tlu.interviewmanagement.service.LevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LevelServiceImpl implements LevelService {
    private final LevelRepository levelRepository;

    @Override
    public List<Level> findAllLevel() {
        return levelRepository.findAll();
    }
}
