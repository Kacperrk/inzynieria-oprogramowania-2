package com.example.demo.service;

import com.example.demo.model.Group;
import com.example.demo.model.Student;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    private StudentRepository studentRepository;
    private GroupRepository groupRepository;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);
        groupRepository = mock(GroupRepository.class);
        studentService = new StudentService(studentRepository, groupRepository);
    }

    private Student sampleStudent() {
        Group group = new Group();
        group.setId(1L);
        group.setName("Group A");

        Student student = new Student();
        student.setId(10L);
        student.setFirstName("Anna");
        student.setLastName("Kowalska");
        student.setGroup(group);
        student.setArchived(false);

        return student;
    }

    @Test
    void shouldCreateStudent() {
        Student student = sampleStudent();

        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.create(student);

        assertThat(result).isEqualTo(student);
        verify(studentRepository).save(student);
    }

    @Test
    void shouldReturnStudentById() {
        Student student = sampleStudent();

        when(studentRepository.findByIdAndArchivedFalse(10L)).thenReturn(Optional.of(student));

        Student result = studentService.getById(10L);

        assertThat(result).isEqualTo(student);
    }

    @Test
    void shouldThrowWhenStudentNotFound() {
        when(studentRepository.findByIdAndArchivedFalse(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.getById(10L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Student not found");
    }

    @Test
    void shouldReturnAllStudents() {
        List<Student> students = List.of(sampleStudent(), sampleStudent());

        when(studentRepository.findAllByArchivedFalse()).thenReturn(students);

        List<Student> result = studentService.getAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldArchiveStudent() {
        Student student = sampleStudent();

        when(studentRepository.findByIdAndArchivedFalse(10L)).thenReturn(Optional.of(student));

        studentService.archive(10L);

        assertThat(student.isArchived()).isTrue();
        verify(studentRepository).save(student);
    }

    @Test
    void shouldThrowWhenArchivingNonExistingStudent() {
        when(studentRepository.findByIdAndArchivedFalse(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.archive(10L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Student not found");
    }

    @Test
    void shouldUpdateStudent() {
        Student existing = sampleStudent();

        Student updated = new Student();
        updated.setFirstName("Jan");
        updated.setLastName("Nowak");
        updated.setGroup(new Group(2L, "Group B", false));

        when(studentRepository.findByIdAndArchivedFalse(10L)).thenReturn(Optional.of(existing));
        when(studentRepository.save(existing)).thenReturn(existing);

        Student result = studentService.update(10L, updated);

        assertThat(result.getFirstName()).isEqualTo("Jan");
        assertThat(result.getLastName()).isEqualTo("Nowak");
        assertThat(result.getGroup().getName()).isEqualTo("Group B");
        verify(studentRepository).save(existing);
    }
}
