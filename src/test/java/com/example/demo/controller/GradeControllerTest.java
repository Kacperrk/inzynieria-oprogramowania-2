package com.example.demo.controller;

import com.example.demo.model.Grade;
import com.example.demo.model.GradeValue;
import com.example.demo.service.GradeService;
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

@WebMvcTest(GradeController.class)
class GradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GradeService gradeService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldReturnGradeById() throws Exception {
        Grade grade = new Grade();
        grade.setId(1L);
        grade.setValue(GradeValue.ONE);

        when(gradeService.getById(1L)).thenReturn(grade);

        mockMvc.perform(get("/api/grades/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.value").value("ONE"));
    }

    @Test
    void shouldCreateGrade() throws Exception {
        Grade grade = new Grade();
        grade.setId(1L);
        grade.setValue(GradeValue.FIVE);

        when(gradeService.create(any(Grade.class))).thenReturn(grade);

        mockMvc.perform(post("/api/grades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(grade)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.value").value("FIVE"));
    }

    @Test
    void shouldDeleteGrade() throws Exception {
        doNothing().when(gradeService).archive(1L);

        mockMvc.perform(delete("/api/grades/1"))
                .andExpect(status().isOk());

        verify(gradeService).archive(1L);
    }

    @Test
    void shouldReturnAllGrades() throws Exception {
        Grade g1 = new Grade(); g1.setId(1L); g1.setValue(GradeValue.ONE);
        Grade g2 = new Grade(); g2.setId(2L); g2.setValue(GradeValue.TWO);

        when(gradeService.getAll()).thenReturn(List.of(g1, g2));

        mockMvc.perform(get("/api/grades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public GradeService gradeService() {
            return Mockito.mock(GradeService.class);
        }
    }
}
