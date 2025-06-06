package com.example.demo.service;

import com.example.demo.model.Subject;
import com.example.demo.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubjectServiceTest {

    private SubjectRepository subjectRepository;
    private SubjectService subjectService;

    @BeforeEach
    void setUp() {
        subjectRepository = mock(SubjectRepository.class);
        subjectService = new SubjectService(subjectRepository);
    }

    private Subject sampleSubject() {
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Mathematics");
        subject.setArchived(false);
        return subject;
    }

    @Test
    void shouldCreateSubject() {
        Subject subject = sampleSubject();

        when(subjectRepository.save(subject)).thenReturn(subject);

        Subject result = subjectService.create(subject);

        assertThat(result).isEqualTo(subject);
        verify(subjectRepository).save(subject);
    }

    @Test
    void shouldReturnSubjectById() {
        Subject subject = sampleSubject();

        when(subjectRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(subject));

        Subject result = subjectService.getById(1L);

        assertThat(result).isEqualTo(subject);
    }

    @Test
    void shouldThrowWhenSubjectNotFound() {
        when(subjectRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> subjectService.getById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Subject not found");
    }

    @Test
    void shouldReturnAllSubjects() {
        List<Subject> subjects = List.of(sampleSubject(), sampleSubject());

        when(subjectRepository.findAllByArchivedFalse()).thenReturn(subjects);

        List<Subject> result = subjectService.getAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldArchiveSubject() {
        Subject subject = sampleSubject();

        when(subjectRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(subject));

        subjectService.archive(1L);

        assertThat(subject.isArchived()).isTrue();
        verify(subjectRepository).save(subject);
    }

    @Test
    void shouldThrowWhenArchivingNonExistingSubject() {
        when(subjectRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> subjectService.archive(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Subject not found");
    }

    @Test
    void shouldUpdateSubject() {
        Subject existing = sampleSubject();
        Subject updated = new Subject();
        updated.setName("Physics");

        when(subjectRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(existing));
        when(subjectRepository.save(existing)).thenReturn(existing);

        Subject result = subjectService.update(1L, updated);

        assertThat(result.getName()).isEqualTo("Physics");
        verify(subjectRepository).save(existing);
    }
}
