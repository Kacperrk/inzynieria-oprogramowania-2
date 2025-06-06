package com.example.demo.service;

import com.example.demo.model.Teacher;
import com.example.demo.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherServiceTest {

    private TeacherRepository teacherRepository;
    private TeacherService teacherService;

    @BeforeEach
    void setUp() {
        teacherRepository = mock(TeacherRepository.class);
        teacherService = new TeacherService(teacherRepository);
    }

    private Teacher sampleTeacher() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("Jan");
        teacher.setLastName("Kowalski");
        teacher.setArchived(false);
        return teacher;
    }

    @Test
    void shouldCreateTeacher() {
        Teacher teacher = sampleTeacher();

        when(teacherRepository.save(teacher)).thenReturn(teacher);

        Teacher result = teacherService.create(teacher);

        assertThat(result).isEqualTo(teacher);
        verify(teacherRepository).save(teacher);
    }

    @Test
    void shouldReturnTeacherById() {
        Teacher teacher = sampleTeacher();

        when(teacherRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(teacher));

        Teacher result = teacherService.getById(1L);

        assertThat(result).isEqualTo(teacher);
    }

    @Test
    void shouldThrowWhenTeacherNotFound() {
        when(teacherRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> teacherService.getById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Teacher not found");
    }

    @Test
    void shouldReturnAllTeachers() {
        List<Teacher> teachers = List.of(sampleTeacher(), sampleTeacher());

        when(teacherRepository.findAllByArchivedFalse()).thenReturn(teachers);

        List<Teacher> result = teacherService.getAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldArchiveTeacher() {
        Teacher teacher = sampleTeacher();

        when(teacherRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(teacher));

        teacherService.archive(1L);

        assertThat(teacher.isArchived()).isTrue();
        verify(teacherRepository).save(teacher);
    }

    @Test
    void shouldThrowWhenArchivingNonExistingTeacher() {
        when(teacherRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> teacherService.archive(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Teacher not found");
    }

    @Test
    void shouldUpdateTeacher() {
        Teacher existing = sampleTeacher();

        Teacher updated = new Teacher();
        updated.setFirstName("Anna");
        updated.setLastName("Nowak");

        when(teacherRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(existing));
        when(teacherRepository.save(existing)).thenReturn(existing);

        Teacher result = teacherService.update(1L, updated);

        assertThat(result.getFirstName()).isEqualTo("Anna");
        assertThat(result.getLastName()).isEqualTo("Nowak");
        verify(teacherRepository).save(existing);
    }
}
