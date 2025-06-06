package com.example.demo.service;

import com.example.demo.model.Group;
import com.example.demo.model.Lesson;
import com.example.demo.model.Subject;
import com.example.demo.model.Teacher;
import com.example.demo.repository.LessonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class LessonServiceTest {

    private LessonRepository lessonRepository;
    private LessonService lessonService;

    @BeforeEach
    void setUp() {
        lessonRepository = mock(LessonRepository.class);
        lessonService = new LessonService(lessonRepository);
    }

    private Lesson sampleLesson() {
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Math");

        Teacher teacher = new Teacher();
        teacher.setId(2L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        Group group = new Group();
        group.setId(3L);
        group.setName("Group A");

        Lesson lesson = new Lesson();
        lesson.setId(10L);
        lesson.setSubject(subject);
        lesson.setTeacher(teacher);
        lesson.setGroup(group);
        lesson.setArchived(false);

        return lesson;
    }

    @Test
    void shouldCreateLesson() {
        Lesson lesson = sampleLesson();

        when(lessonRepository.save(lesson)).thenReturn(lesson);

        Lesson result = lessonService.create(lesson);

        assertThat(result).isEqualTo(lesson);
        verify(lessonRepository).save(lesson);
    }

    @Test
    void shouldReturnLessonById() {
        Lesson lesson = sampleLesson();

        when(lessonRepository.findByIdAndArchivedFalse(10L)).thenReturn(Optional.of(lesson));

        Lesson result = lessonService.getById(10L);

        assertThat(result).isEqualTo(lesson);
    }

    @Test
    void shouldThrowWhenLessonNotFound() {
        when(lessonRepository.findByIdAndArchivedFalse(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> lessonService.getById(10L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Lesson not found");
    }

    @Test
    void shouldReturnAllLessons() {
        List<Lesson> lessons = List.of(sampleLesson(), sampleLesson());

        when(lessonRepository.findAllByArchivedFalse()).thenReturn(lessons);

        List<Lesson> result = lessonService.getAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldArchiveLesson() {
        Lesson lesson = sampleLesson();
        lesson.setArchived(false);

        when(lessonRepository.findByIdAndArchivedFalse(10L)).thenReturn(Optional.of(lesson));

        lessonService.archive(10L);

        assertThat(lesson.isArchived()).isTrue();
        verify(lessonRepository).save(lesson);
    }

    @Test
    void shouldThrowWhenArchivingNonExistingLesson() {
        when(lessonRepository.findByIdAndArchivedFalse(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> lessonService.archive(10L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Lesson not found");
    }

    @Test
    void shouldUpdateLesson() {
        Lesson existing = sampleLesson();
        Lesson updated = sampleLesson();
        updated.setSubject(new Subject(99L, "Physics", false));
        updated.setTeacher(new Teacher(88L, "Anna", "Smith", false));
        updated.setGroup(new Group(77L, "Group B", false));

        when(lessonRepository.findByIdAndArchivedFalse(10L)).thenReturn(Optional.of(existing));
        when(lessonRepository.save(existing)).thenReturn(existing);

        Lesson result = lessonService.update(10L, updated);

        assertThat(result.getSubject().getId()).isEqualTo(99L);
        assertThat(result.getTeacher().getFirstName()).isEqualTo("Anna");
        assertThat(result.getGroup().getName()).isEqualTo("Group B");
        verify(lessonRepository).save(existing);
    }
}
