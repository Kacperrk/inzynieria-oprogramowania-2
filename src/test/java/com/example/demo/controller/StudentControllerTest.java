package com.example.demo.controller;

import com.example.demo.model.Group;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
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

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Student sampleStudent(Long id) {
        Group group = new Group();
        group.setId(100L);
        group.setName("Group A");

        Student student = new Student();
        student.setId(id);
        student.setFirstName("Anna");
        student.setLastName("Kowalska");
        student.setGroup(group);
        student.setArchived(false);

        return student;
    }

    @Test
    void shouldReturnStudentById() throws Exception {
        Student student = sampleStudent(1L);

        when(studentService.getById(1L)).thenReturn(student);

        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Anna"))
                .andExpect(jsonPath("$.lastName").value("Kowalska"))
                .andExpect(jsonPath("$.group.name").value("Group A"))
                .andExpect(jsonPath("$.archived").value(false));
    }

    @Test
    void shouldCreateStudent() throws Exception {
        Student student = sampleStudent(1L);

        when(studentService.create(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Anna"))
                .andExpect(jsonPath("$.lastName").value("Kowalska"))
                .andExpect(jsonPath("$.group.name").value("Group A"))
                .andExpect(jsonPath("$.archived").value(false));
    }

    @Test
    void shouldReturnAllStudents() throws Exception {
        Student s1 = sampleStudent(1L);
        Student s2 = sampleStudent(2L);

        when(studentService.getAll()).thenReturn(List.of(s1, s2));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldUpdateStudent() throws Exception {
        Student updated = sampleStudent(1L);
        updated.setArchived(true);

        when(studentService.update(eq(1L), any(Student.class))).thenReturn(updated);

        mockMvc.perform(put("/api/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.archived").value(true));
    }

    @Test
    void shouldDeleteStudent() throws Exception {
        doNothing().when(studentService).archive(1L);

        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isOk());

        verify(studentService).archive(1L);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public StudentService studentService() {
            return Mockito.mock(StudentService.class);
        }
    }
}
