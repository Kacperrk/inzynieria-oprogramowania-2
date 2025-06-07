package com.example.demo.service;

import com.example.demo.model.Application;
import com.example.demo.repository.ApplicationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public Application create(Application application) {
        return applicationRepository.save(application);
    }

    public Application getById(Long id) {
        return applicationRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));
    }

    public List<Application> getAll() {
        return applicationRepository.findAllByArchivedFalse();
    }

    public void archive(Long id) {
        Application application = getById(id);
        application.setArchived(true);
        applicationRepository.save(application);
    }

    public Application update(Long id, Application updatedApplication) {
        Application application = getById(id);
        application.setTitle(updatedApplication.getTitle());
        application.setDescription(updatedApplication.getDescription());
        application.setType(updatedApplication.getType());
        application.setStatus(updatedApplication.getStatus());
        application.setStudent(updatedApplication.getStudent());
        application.setParent(updatedApplication.getParent());
        application.setProcessedDate(updatedApplication.getProcessedDate());
        application.setResponse(updatedApplication.getResponse());
        return applicationRepository.save(application);
    }
}