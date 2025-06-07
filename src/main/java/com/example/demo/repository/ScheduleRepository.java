package com.example.demo.repository;

import com.example.demo.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findByIdAndArchivedFalse(Long id);
    List<Schedule> findAllByArchivedFalse();
}