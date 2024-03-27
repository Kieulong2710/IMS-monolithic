package com.tlu.interviewmanagement.service.impl;

import com.tlu.interviewmanagement.entities.Department;
import com.tlu.interviewmanagement.repository.DepartmentRepository;
import com.tlu.interviewmanagement.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    @Override
    public List<Department> findAllDepartment() {
        return departmentRepository.findAll();
    }
}
