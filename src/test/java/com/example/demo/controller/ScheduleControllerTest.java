package com.example.demo.controller;
import com.example.demo.model.Subject;
import com.example.demo.model.Group;
import com.example.demo.model.Schedule;
import com.example.demo.model.Teacher;
import com.example.demo.service.ScheduleService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ScheduleController.class)
class ScheduleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ScheduleService scheduleService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Schedule sampleSchedule(Long id) {
    Schedule schedule = new Schedule();
    schedule.setId(id);

    Group group = new Group();
    group.setId(1L);
    group.setName("Group A");
    group.setArchived(false);
    schedule.setGroup(group);

    Subject subject = new Subject();
    subject.setId(1L);
    subject.setName("Math");
    subject.setArchived(false);
    schedule.setSubject(subject);

    Teacher teacher = new Teacher();
    teacher.setId(1L);
    teacher.setFirstName("Jan");
    teacher.setLastName("Nowak");
    teacher.setArchived(false);
    schedule.setTeacher(teacher);

    schedule.setDayOfWeek("Monday");
    schedule.setStartTime("08:00");
    schedule.setEndTime("09:30");
    schedule.setArchived(false);

    return schedule;
}


    @Test
    void shouldReturnScheduleById() throws Exception {
        Schedule schedule = sampleSchedule(1L);
        when(scheduleService.getById(1L)).thenReturn(schedule);

        mockMvc.perform(get("/api/schedules/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.group.name").value("Group A"))
                .andExpect(jsonPath("$.subject.name").value("Math"))
                .andExpect(jsonPath("$.teacher.firstName").value("Jan"))
                .andExpect(jsonPath("$.teacher.lastName").value("Nowak"))
                .andExpect(jsonPath("$.dayOfWeek").value("Monday"))
                .andExpect(jsonPath("$.startTime").value("08:00"))
                .andExpect(jsonPath("$.endTime").value("09:30"))
                .andExpect(jsonPath("$.archived").value(false));
    }

    @Test
    void shouldCreateSchedule() throws Exception {
        Schedule schedule = sampleSchedule(1L);
        when(scheduleService.create(any(Schedule.class))).thenReturn(schedule);

        mockMvc.perform(post("/api/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(schedule)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.group.name").value("Group A"))
                .andExpect(jsonPath("$.subject.name").value("Math"))
                .andExpect(jsonPath("$.teacher.firstName").value("Jan"))
                .andExpect(jsonPath("$.teacher.lastName").value("Nowak"))
                .andExpect(jsonPath("$.dayOfWeek").value("Monday"))
                .andExpect(jsonPath("$.startTime").value("08:00"))
                .andExpect(jsonPath("$.endTime").value("09:30"));

    }

    @Test
    void shouldReturnAllSchedules() throws Exception {
        Schedule s1 = sampleSchedule(1L);
        Schedule s2 = sampleSchedule(2L);
        when(scheduleService.getAll()).thenReturn(List.of(s1, s2));

        mockMvc.perform(get("/api/schedules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldUpdateSchedule() throws Exception {
        Schedule updated = sampleSchedule(1L);
        updated.setArchived(true);
        when(scheduleService.update(eq(1L), any(Schedule.class))).thenReturn(updated);

        mockMvc.perform(put("/api/schedules/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.archived").value(true));
    }

    @Test
    void shouldDeleteSchedule() throws Exception {
        doNothing().when(scheduleService).archive(1L);

        mockMvc.perform(delete("/api/schedules/1"))
                .andExpect(status().isOk());

        verify(scheduleService).archive(1L);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ScheduleService scheduleService() {
            return Mockito.mock(ScheduleService.class);
        }
    }
}