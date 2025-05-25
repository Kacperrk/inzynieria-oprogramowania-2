package com.example.demo.repository;

import com.example.demo.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByIdAndArchivedFalse(Long id);
    List<Teacher> findAllByArchivedFalse();
}
