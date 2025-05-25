package com.example.demo.repository;

import com.example.demo.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByIdAndArchivedFalse(Long id);
    List<Subject> findAllByArchivedFalse();
}
