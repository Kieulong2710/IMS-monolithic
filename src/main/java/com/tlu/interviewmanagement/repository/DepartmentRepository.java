package com.tlu.interviewmanagement.repository;

import com.tlu.interviewmanagement.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
