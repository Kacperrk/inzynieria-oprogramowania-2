package com.example.demo.controller;

import com.example.demo.model.Group;
import com.example.demo.model.Lesson;
import com.example.demo.model.Subject;
import com.example.demo.model.Teacher;
import com.example.demo.service.LessonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasSize;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LessonController.class)
class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LessonService lessonService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Lesson sampleLesson(Long id) {
        Subject subject = new Subject();
        subject.setId(10L);
        subject.setName("Math");

        Teacher teacher = new Teacher();
        teacher.setId(20L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        Group group = new Group();
        group.setId(30L);
        group.setName("Group A");

        Lesson lesson = new Lesson();
        lesson.setId(id);
        lesson.setSubject(subject);
        lesson.setTeacher(teacher);
        lesson.setGroup(group);
        lesson.setArchived(false);

        return lesson;
    }

    @Test
    void shouldReturnLessonById() throws Exception {
        Lesson lesson = sampleLesson(1L);

        when(lessonService.getById(1L)).thenReturn(lesson);

        mockMvc.perform(get("/api/lessons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.subject.name").value("Math"))
                .andExpect(jsonPath("$.teacher.firstName").value("John"))
                .andExpect(jsonPath("$.group.name").value("Group A"))
                .andExpect(jsonPath("$.archived").value(false));
    }

    @Test
    void shouldCreateLesson() throws Exception {
        Lesson lesson = sampleLesson(1L);

        when(lessonService.create(any(Lesson.class))).thenReturn(lesson);

        mockMvc.perform(post("/api/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lesson)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.subject.name").value("Math"))
                .andExpect(jsonPath("$.teacher.firstName").value("John"))
                .andExpect(jsonPath("$.group.name").value("Group A"))
                .andExpect(jsonPath("$.archived").value(false));
    }

    @Test
    void shouldReturnAllLessons() throws Exception {
        Lesson l1 = sampleLesson(1L);
        Lesson l2 = sampleLesson(2L);

        when(lessonService.getAll()).thenReturn(List.of(l1, l2));

        mockMvc.perform(get("/api/lessons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldUpdateLesson() throws Exception {
        Lesson updatedLesson = sampleLesson(1L);
        updatedLesson.setArchived(true);

        when(lessonService.update(eq(1L), any(Lesson.class))).thenReturn(updatedLesson);

        mockMvc.perform(put("/api/lessons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedLesson)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.archived").value(true));
    }

    @Test
    void shouldDeleteLesson() throws Exception {
        doNothing().when(lessonService).archive(1L);

        mockMvc.perform(delete("/api/lessons/1"))
                .andExpect(status().isOk());

        verify(lessonService).archive(1L);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public LessonService lessonService() {
            return Mockito.mock(LessonService.class);
        }
    }
}
