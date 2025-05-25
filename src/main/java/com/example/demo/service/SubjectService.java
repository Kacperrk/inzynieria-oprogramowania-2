package com.example.demo.service;

import com.example.demo.model.Subject;
import com.example.demo.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public Subject create(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Subject getById(Long id) {
        return subjectRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found"));
    }

    public List<Subject> getAll() {
        return subjectRepository.findAllByArchivedFalse();
    }

    public void archive(Long id) {
        Subject subject = getById(id);
        subject.setArchived(true);
        subjectRepository.save(subject);
    }

    public Subject update(Long id, Subject updatedSubject) {
        Subject subject = getById(id);
        subject.setName(updatedSubject.getName());
        return subjectRepository.save(subject);
    }
}
