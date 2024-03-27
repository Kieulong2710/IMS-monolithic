package com.tlu.interviewmanagement.repository;

import com.tlu.interviewmanagement.entities.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LevelRepository extends JpaRepository<Level, Long> {
    Optional<Level> findAllByName(String name);
}
