package com.example.demo.controller;

import com.example.demo.model.Subject;
import com.example.demo.service.SubjectService;
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

@WebMvcTest(SubjectController.class)
class SubjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SubjectService subjectService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Subject sampleSubject(Long id) {
        Subject subject = new Subject();
        subject.setId(id);
        subject.setName("Mathematics");
        subject.setArchived(false);
        return subject;
    }

    @Test
    void shouldReturnSubjectById() throws Exception {
        Subject subject = sampleSubject(1L);

        when(subjectService.getById(1L)).thenReturn(subject);

        mockMvc.perform(get("/api/subjects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Mathematics"))
                .andExpect(jsonPath("$.archived").value(false));
    }

    @Test
    void shouldCreateSubject() throws Exception {
        Subject subject = sampleSubject(1L);

        when(subjectService.create(any(Subject.class))).thenReturn(subject);

        mockMvc.perform(post("/api/subjects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subject)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Mathematics"))
                .andExpect(jsonPath("$.archived").value(false));
    }

    @Test
    void shouldReturnAllSubjects() throws Exception {
        Subject s1 = sampleSubject(1L);
        Subject s2 = sampleSubject(2L);

        when(subjectService.getAll()).thenReturn(List.of(s1, s2));

        mockMvc.perform(get("/api/subjects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldUpdateSubject() throws Exception {
        Subject updated = sampleSubject(1L);
        updated.setName("Physics");
        updated.setArchived(true);

        when(subjectService.update(eq(1L), any(Subject.class))).thenReturn(updated);

        mockMvc.perform(put("/api/subjects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Physics"))
                .andExpect(jsonPath("$.archived").value(true));
    }

    @Test
    void shouldDeleteSubject() throws Exception {
        doNothing().when(subjectService).archive(1L);

        mockMvc.perform(delete("/api/subjects/1"))
                .andExpect(status().isOk());

        verify(subjectService).archive(1L);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public SubjectService subjectService() {
            return Mockito.mock(SubjectService.class);
        }
    }
}
