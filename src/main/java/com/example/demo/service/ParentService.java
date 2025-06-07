package com.example.demo.service;

import com.example.demo.model.Parent;
import com.example.demo.repository.ParentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentService {
    private final ParentRepository parentRepository;

    public Parent create(Parent parent) {
        return parentRepository.save(parent);
    }

    public Parent getById(Long id) {
        return parentRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Parent not found"));
    }

    public List<Parent> getAll() {
        return parentRepository.findAllByArchivedFalse();
    }

    public void archive(Long id) {
        Parent parent = getById(id);
        parent.setArchived(true);
        parentRepository.save(parent);
    }

    public Parent update(Long id, Parent updatedParent) {
        Parent parent = getById(id);
        parent.setFirstName(updatedParent.getFirstName());
        parent.setLastName(updatedParent.getLastName());
        parent.setEmail(updatedParent.getEmail());
        parent.setPhone(updatedParent.getPhone());
        parent.setAddress(updatedParent.getAddress());
        return parentRepository.save(parent);
    }
}