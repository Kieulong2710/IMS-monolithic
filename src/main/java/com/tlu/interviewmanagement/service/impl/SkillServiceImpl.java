package com.tlu.interviewmanagement.service.impl;

import com.tlu.interviewmanagement.entities.Skill;
import com.tlu.interviewmanagement.repository.SkillRepository;
import com.tlu.interviewmanagement.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;
    @Override
    public List<Skill> findAllSkill() {
        return skillRepository.findAll();
    }
}
