package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

import com.example.demo.model.Parent;
import com.example.demo.service.ParentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ParentController.class)
class ParentControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParentService parentService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Parent sampleParent(Long id) {
        Parent parent = new Parent();
        parent.setId(id);
        parent.setFirstName("Jan");
        parent.setLastName("Nowak");
        parent.setEmail("example@example.com");
        parent.setPhone("123456789");
        parent.setAddress("ul. Przykładowa 1, Warszawa");
        parent.setArchived(false);

        return parent;
    }

    @Test
    void shouldReturnParentById() throws Exception {
        Parent parent = sampleParent(1L);

        when(parentService.getById(1L)).thenReturn(parent);

        mockMvc.perform(get("/api/parents/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Jan"))
                .andExpect(jsonPath("$.lastName").value("Nowak"))
                .andExpect(jsonPath("$.email").value("example@example.com"))
                .andExpect(jsonPath("$.phone").value("123456789"))
                .andExpect(jsonPath("$.address").value("ul. Przykładowa 1, Warszawa"));        
    }

    @Test
    void shouldCreateParent() throws Exception {
        Parent parent = sampleParent(1L);

        when(parentService.create(any(Parent.class))).thenReturn(parent);

        mockMvc.perform(post("/api/parents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parent)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.firstName").value("Jan"))
                    .andExpect(jsonPath("$.lastName").value("Nowak"))
                    .andExpect(jsonPath("$.email").value("example@example.com"))
                    .andExpect(jsonPath("$.phone").value("123456789"))
                    .andExpect(jsonPath("$.address").value("ul. Przykładowa 1, Warszawa"));        
    }

    @Test
    void shouldReturnAllParents() throws Exception {
        Parent p1 = sampleParent(1L);
        Parent p2 = sampleParent(2L);

        when(parentService.getAll()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/api/parents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldUpdateParent() throws Exception {
        Parent updated = sampleParent(1L);
        updated.setArchived(true);

        when(parentService.update(eq(1L), any(Parent.class))).thenReturn(updated);

        mockMvc.perform(put("/api/parents/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.archived").value(true));
    }

    @Test
    void shouldDeleteParent() throws Exception {
        doNothing().when(parentService).archive(1L);

        mockMvc.perform(delete("/api/parents/1"))
                .andExpect(status().isOk());

        verify(parentService).archive(1L);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ParentService parentService() {
            return Mockito.mock(ParentService.class);
        }
    }
}