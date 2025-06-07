package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.model.Application;
import com.example.demo.model.ApplicationType;
import com.example.demo.model.ApplicationStatus;
import com.example.demo.model.Student;
import com.example.demo.model.Parent;
import com.example.demo.service.ApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(ApplicationController.class)
class ApplicationControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationService applicationService;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


    private Application sampleApplication(Long id) {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Jan");
        student.setLastName("Kowalski");

        Parent parent = new Parent();
        parent.setId(1L);
        parent.setFirstName("Anna");
        parent.setLastName("Kowalska");

        Application application = new Application();
        application.setId(id);
        application.setTitle("Excuse for absence");
        application.setDescription("Please excuse the absence on 15.01.2025");
        application.setType(ApplicationType.ABSENCE_EXCUSE);
        application.setStatus(ApplicationStatus.PENDING);
        application.setStudent(student);
        application.setParent(parent);
        application.setCreatedDate(LocalDateTime.of(2025, 1, 15, 10, 30, 0));
        application.setArchived(false);

        return application;
    }

    @Test
    void shouldReturnApplicationById() throws Exception {
        Application application = sampleApplication(1L);

        when(applicationService.getById(1L)).thenReturn(application);

        mockMvc.perform(get("/api/applications/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Excuse for absence"))
                .andExpect(jsonPath("$.description").value("Please excuse the absence on 15.01.2025"))
                .andExpect(jsonPath("$.type").value("ABSENCE_EXCUSE"))
                .andExpect(jsonPath("$.status").value("PENDING"));        
    }

    @Test
    void shouldCreateApplication() throws Exception {
        Application application = sampleApplication(1L);

        when(applicationService.create(any(Application.class))).thenReturn(application);

        mockMvc.perform(post("/api/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(application)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.title").value("Excuse for absence"))
                    .andExpect(jsonPath("$.description").value("Please excuse the absence on 15.01.2025"))
                    .andExpect(jsonPath("$.type").value("ABSENCE_EXCUSE"))
                    .andExpect(jsonPath("$.status").value("PENDING"));        
    }

    @Test
    void shouldReturnAllApplications() throws Exception {
        Application a1 = sampleApplication(1L);
        Application a2 = sampleApplication(2L);

        when(applicationService.getAll()).thenReturn(List.of(a1, a2));

        mockMvc.perform(get("/api/applications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldUpdateApplication() throws Exception {
        Application updated = sampleApplication(1L);
        updated.setStatus(ApplicationStatus.APPROVED);
        updated.setArchived(true);

        when(applicationService.update(eq(1L), any(Application.class))).thenReturn(updated);

        mockMvc.perform(put("/api/applications/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("APPROVED"))
                .andExpect(jsonPath("$.archived").value(true));
    }

    @Test
    void shouldDeleteApplication() throws Exception {
        doNothing().when(applicationService).archive(1L);

        mockMvc.perform(delete("/api/applications/1"))
                .andExpect(status().isOk());

        verify(applicationService).archive(1L);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ApplicationService applicationService() {
            return Mockito.mock(ApplicationService.class);
        }
    }
}
