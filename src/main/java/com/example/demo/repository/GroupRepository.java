package com.example.demo.repository;

import com.example.demo.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByIdAndArchivedFalse(Long id);
    List<Group> findAllByArchivedFalse();
}
