package com.example.demo.controller;

import com.example.demo.model.Teacher;
import com.example.demo.service.TeacherService;
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

@WebMvcTest(TeacherController.class)
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherService teacherService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Teacher sampleTeacher(Long id) {
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setFirstName("Jan");
        teacher.setLastName("Nowak");
        teacher.setArchived(false);
        return teacher;
    }

    @Test
    void shouldReturnTeacherById() throws Exception {
        Teacher teacher = sampleTeacher(1L);

        when(teacherService.getById(1L)).thenReturn(teacher);

        mockMvc.perform(get("/api/teachers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Jan"))
                .andExpect(jsonPath("$.lastName").value("Nowak"))
                .andExpect(jsonPath("$.archived").value(false));
    }

    @Test
    void shouldCreateTeacher() throws Exception {
        Teacher teacher = sampleTeacher(1L);

        when(teacherService.create(any(Teacher.class))).thenReturn(teacher);

        mockMvc.perform(post("/api/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teacher)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Jan"))
                .andExpect(jsonPath("$.lastName").value("Nowak"))
                .andExpect(jsonPath("$.archived").value(false));
    }

    @Test
    void shouldReturnAllTeachers() throws Exception {
        Teacher t1 = sampleTeacher(1L);
        Teacher t2 = sampleTeacher(2L);

        when(teacherService.getAll()).thenReturn(List.of(t1, t2));

        mockMvc.perform(get("/api/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldUpdateTeacher() throws Exception {
        Teacher updated = sampleTeacher(1L);
        updated.setArchived(true);

        when(teacherService.update(eq(1L), any(Teacher.class))).thenReturn(updated);

        mockMvc.perform(put("/api/teachers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.archived").value(true));
    }

    @Test
    void shouldDeleteTeacher() throws Exception {
        doNothing().when(teacherService).archive(1L);

        mockMvc.perform(delete("/api/teachers/1"))
                .andExpect(status().isOk());

        verify(teacherService).archive(1L);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public TeacherService teacherService() {
            return Mockito.mock(TeacherService.class);
        }
    }
}
