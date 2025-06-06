package com.example.demo.service;

import com.example.demo.model.Grade;
import com.example.demo.model.GradeValue;
import com.example.demo.repository.GradeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class GradeServiceTest {

    private GradeRepository gradeRepository;
    private GradeService gradeService;

    @BeforeEach
    void setUp() {
        gradeRepository = mock(GradeRepository.class);
        gradeService = new GradeService(gradeRepository);
    }

    @Test
    void shouldCreateValidGrade() {
        Grade input = new Grade();
        input.setValue(GradeValue.ONE);
        when(gradeRepository.save(input)).thenReturn(input);

        Grade result = gradeService.create(input);

        assertThat(result).isEqualTo(input);
        verify(gradeRepository).save(input);
    }

    @Test
    void shouldThrowOnInvalidValue() {
        Grade invalid = new Grade();
        invalid.setValue(null);

        assertThatThrownBy(() -> gradeService.create(invalid))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid value");
    }

    @Test
    void shouldReturnGradeById() {
        Grade grade = new Grade();
        when(gradeRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(grade));

        Grade result = gradeService.getById(1L);

        assertThat(result).isEqualTo(grade);
    }

    @Test
    void shouldThrowWhenGradeNotFound() {
        when(gradeRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gradeService.getById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Grade not found");
    }

    @Test
    void shouldArchiveGrade() {
        Grade grade = new Grade();
        grade.setArchived(false);
        when(gradeRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(grade));

        gradeService.archive(1L);

        assertThat(grade.isArchived()).isTrue();
        verify(gradeRepository).save(grade);
    }

    @Test
    void shouldReturnAllGrades() {
        List<Grade> grades = List.of(new Grade(), new Grade());
        when(gradeRepository.findAllByArchivedFalse()).thenReturn(grades);

        List<Grade> result = gradeService.getAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldThrowWhenArchivingNonExistingGrade() {
        when(gradeRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gradeService.archive(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Grade not found");
    }
}
