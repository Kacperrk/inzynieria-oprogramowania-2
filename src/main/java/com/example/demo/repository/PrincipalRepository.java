package com.example.demo.repository;

import com.example.demo.model.Principal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface PrincipalRepository extends JpaRepository<Principal, Long> {
    Optional<Principal> findByIdAndArchivedFalse(Long id);
    List<Principal> findAllByArchivedFalse();
}
