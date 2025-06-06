package com.example.demo.controller;

import com.example.demo.model.Group;
import com.example.demo.service.GroupService;
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

@WebMvcTest(GroupController.class)
class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GroupService groupService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldReturnGroupById() throws Exception {
        Group group = new Group();
        group.setId(1L);
        group.setName("Group A");

        when(groupService.getById(1L)).thenReturn(group);

        mockMvc.perform(get("/api/groups/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Group A"));
    }

    @Test
    void shouldCreateGroup() throws Exception {
        Group group = new Group();
        group.setId(1L);
        group.setName("Group B");

        when(groupService.create(any(Group.class))).thenReturn(group);

        mockMvc.perform(post("/api/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(group)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Group B"));
    }

    @Test
    void shouldReturnAllGroups() throws Exception {
        Group g1 = new Group(); g1.setId(1L); g1.setName("G1");
        Group g2 = new Group(); g2.setId(2L); g2.setName("G2");

        when(groupService.getAll()).thenReturn(List.of(g1, g2));

        mockMvc.perform(get("/api/groups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldUpdateGroup() throws Exception {
        Group updatedGroup = new Group();
        updatedGroup.setId(1L);
        updatedGroup.setName("Updated Group");

        when(groupService.update(eq(1L), any(Group.class))).thenReturn(updatedGroup);

        mockMvc.perform(put("/api/groups/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedGroup)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Group"));
    }

    @Test
    void shouldDeleteGroup() throws Exception {
        doNothing().when(groupService).archive(1L);

        mockMvc.perform(delete("/api/groups/1"))
                .andExpect(status().isOk());

        verify(groupService).archive(1L);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public GroupService groupService() {
            return Mockito.mock(GroupService.class);
        }
    }
}
