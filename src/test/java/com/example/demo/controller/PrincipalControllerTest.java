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

import com.example.demo.model.Principal;
import com.example.demo.service.PrincipalService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PrincipalController.class)
class PrincipalControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PrincipalService principalService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Principal samplePrincipal(Long id) {
        Principal principal = new Principal();
        principal.setId(id);
        principal.setFirstName("Jan");
        principal.setLastName("Nowak");
        principal.setEmail("example@example.com");
        principal.setPhone("123456789");
        principal.setArchived(false);

        return principal;
    }

    @Test
    void shouldReturnPrincipalById() throws Exception {
        Principal principal = samplePrincipal(1L);

        when(principalService.getById(1L)).thenReturn(principal);

        mockMvc.perform(get("/api/principals/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Jan"))
                .andExpect(jsonPath("$.lastName").value("Nowak"))
                .andExpect(jsonPath("$.email").value("example@example.com"))
                .andExpect(jsonPath("$.phone").value("123456789"));        
    }

    @Test
    void shouldCreatePrincipal() throws Exception {
        Principal principal = samplePrincipal(1L);

        when(principalService.create(any(Principal.class))).thenReturn(principal);

        mockMvc.perform(post("/api/principals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(principal)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.firstName").value("Jan"))
                    .andExpect(jsonPath("$.lastName").value("Nowak"))
                    .andExpect(jsonPath("$.email").value("example@example.com"))
                    .andExpect(jsonPath("$.phone").value("123456789"));        
    }

    @Test
    void shouldReturnAllPrincipals() throws Exception {
        Principal p1 = samplePrincipal(1L);
        Principal p2 = samplePrincipal(2L);

        when(principalService.getAll()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/api/principals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldUpdatePrincipal() throws Exception {
        Principal updated = samplePrincipal(1L);
        updated.setArchived(true);

        when(principalService.update(eq(1L), any(Principal.class))).thenReturn(updated);

        mockMvc.perform(put("/api/principals/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.archived").value(true));
    }

    @Test
    void shouldDeletePrincipal() throws Exception {
        doNothing().when(principalService).archive(1L);

        mockMvc.perform(delete("/api/principals/1"))
                .andExpect(status().isOk());

        verify(principalService).archive(1L);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public PrincipalService principalService() {
            return Mockito.mock(PrincipalService.class);
        }
    }
}
