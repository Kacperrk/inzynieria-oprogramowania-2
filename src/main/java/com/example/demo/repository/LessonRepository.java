package com.example.demo.repository;

import com.example.demo.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Optional<Lesson> findByIdAndArchivedFalse(Long id);
    List<Lesson> findAllByArchivedFalse();
}
