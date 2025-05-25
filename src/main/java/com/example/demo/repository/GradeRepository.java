package com.example.demo.repository;

import com.example.demo.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    Optional<Grade> findByIdAndArchivedFalse(Long id);
    List<Grade> findAllByArchivedFalse();
}
