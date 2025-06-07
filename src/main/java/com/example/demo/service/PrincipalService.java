package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.model.Principal;
import com.example.demo.repository.PrincipalRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrincipalService {
    private final PrincipalRepository principalRepository;

    public Principal create(Principal principal) {
        return principalRepository.save(principal);
    }

    public Principal getById(Long id) {
        return principalRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Principal not found"));
    }

    public List<Principal> getAll() {
        return principalRepository.findAllByArchivedFalse();
     }

     public void archive(Long id) {
        Principal principal = getById(id);
        principal.setArchived(true);
        principalRepository.save(principal);
     }

     public Principal update(Long id, Principal updatedPrincipal) {
        Principal principal = getById(id);
        principal.setFirstName(updatedPrincipal.getFirstName());
        principal.setLastName(updatedPrincipal.getLastName());
        principal.setEmail(updatedPrincipal.getEmail());
        principal.setPhone(updatedPrincipal.getPhone());
        return principalRepository.save(principal);
    }
}
