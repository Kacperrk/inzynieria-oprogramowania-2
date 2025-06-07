package com.example.demo.service;

import com.example.demo.model.Application;
import com.example.demo.model.ApplicationType;
import com.example.demo.model.ApplicationStatus;
import com.example.demo.model.Student;
import com.example.demo.model.Parent;
import com.example.demo.repository.ApplicationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationServiceTest {

    private ApplicationRepository applicationRepository;
    private ApplicationService applicationService;

    @BeforeEach
    void setUp() {
        applicationRepository = mock(ApplicationRepository.class);
        applicationService = new ApplicationService(applicationRepository);
    }

    private Application sampleApplication() {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Jan");
        student.setLastName("Kowalski");

        Parent parent = new Parent();
        parent.setId(1L);
        parent.setFirstName("Anna");
        parent.setLastName("Kowalska");

        Application application = new Application();
        application.setId(1L);
        application.setTitle("Excuse for absence");
        application.setDescription("Please excuse the absence on 15.01.2025");
        application.setType(ApplicationType.ABSENCE_EXCUSE);
        application.setStatus(ApplicationStatus.PENDING);
        application.setStudent(student);
        application.setParent(parent);
        application.setCreatedDate(LocalDateTime.now());
        application.setArchived(false);
        return application;
    }

    @Test
    void shouldCreateApplication() {
        Application application = sampleApplication();

        when(applicationRepository.save(application)).thenReturn(application);

        Application result = applicationService.create(application);

        assertThat(result).isEqualTo(application);
        verify(applicationRepository).save(application);
    }

    @Test
    void shouldReturnApplicationById() {
        Application application = sampleApplication();

        when(applicationRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(application));

        Application result = applicationService.getById(1L);

        assertThat(result).isEqualTo(application);
    }

    @Test
    void shouldThrowWhenApplicationNotFound() {
        when(applicationRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> applicationService.getById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Application not found");
    }

    @Test
    void shouldReturnAllApplications() {
        List<Application> applications = List.of(sampleApplication(), sampleApplication());

        when(applicationRepository.findAllByArchivedFalse()).thenReturn(applications);

        List<Application> result = applicationService.getAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("Excuse for absence");
    }

    @Test
    void shouldArchiveApplication() {
        Application application = sampleApplication();
        application.setArchived(false);

        when(applicationRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(application));

        applicationService.archive(1L);

        assertThat(application.isArchived()).isTrue();
        verify(applicationRepository).save(application);
    }

    @Test
    void shouldThrowWhenArchivingNonExistingApplication() {
        when(applicationRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> applicationService.archive(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Application not found");
    }

    @Test
    void shouldUpdateApplication() {
        Application existing = sampleApplication();
        Application updated = new Application();
        updated.setTitle("Request for additional classes");
        updated.setDescription("Please organize additional math classes");
        updated.setType(ApplicationType.ADDITIONAL_CLASSES);
        updated.setStatus(ApplicationStatus.APPROVED);
        updated.setProcessedDate(LocalDateTime.now());
        updated.setResponse("The application has been approved");

        when(applicationRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(existing));
        when(applicationRepository.save(existing)).thenReturn(existing);

        Application result = applicationService.update(1L, updated);

        assertThat(result.getTitle()).isEqualTo("Request for additional classes");
        assertThat(result.getDescription()).isEqualTo("Please organize additional math classes");
        assertThat(result.getType()).isEqualTo(ApplicationType.ADDITIONAL_CLASSES);
        assertThat(result.getStatus()).isEqualTo(ApplicationStatus.APPROVED);
        assertThat(result.getResponse()).isEqualTo("The application has been approved");

        verify(applicationRepository).save(existing);
    }
}
