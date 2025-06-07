package com.example.demo.service;

import com.example.demo.model.Group;
import com.example.demo.model.Schedule;
import com.example.demo.model.Subject;
import com.example.demo.model.Teacher;
import com.example.demo.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScheduleServiceTest {

    private ScheduleRepository scheduleRepository;
    private ScheduleService scheduleService;

    @BeforeEach
    void setUp() {
        scheduleRepository = mock(ScheduleRepository.class);
        scheduleService = new ScheduleService(scheduleRepository);
    }

    private Schedule sampleSchedule() {
        Group group = new Group();
        group.setId(1L);
        group.setName("Group A");

        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Math");

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("Jan");
        teacher.setLastName("Nowak");

        Schedule schedule = new Schedule();
        schedule.setId(1L);
        schedule.setGroup(group);
        schedule.setSubject(subject);
        schedule.setTeacher(teacher);
        schedule.setDayOfWeek("Monday");
        schedule.setStartTime("08:00");
        schedule.setEndTime("09:30");
        schedule.setArchived(false);
        return schedule;
    }

    @Test
    void shouldCreateSchedule() {
        Schedule schedule = sampleSchedule();

        when(scheduleRepository.save(schedule)).thenReturn(schedule);

        Schedule result = scheduleService.create(schedule);

        assertThat(result).isEqualTo(schedule);
        verify(scheduleRepository).save(schedule);
    }

    @Test
    void shouldReturnScheduleById() {
        Schedule schedule = sampleSchedule();

        when(scheduleRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(schedule));

        Schedule result = scheduleService.getById(1L);

        assertThat(result).isEqualTo(schedule);
    }

    @Test
    void shouldThrowWhenScheduleNotFound() {
        when(scheduleRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> scheduleService.getById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Schedule not found");
    }

    @Test
    void shouldReturnAllSchedules() {
        List<Schedule> schedules = List.of(sampleSchedule(), sampleSchedule());

        when(scheduleRepository.findAllByArchivedFalse()).thenReturn(schedules);

        List<Schedule> result = scheduleService.getAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getGroup().getName()).isEqualTo("Group A");
    }

    @Test
    void shouldArchiveSchedule() {
        Schedule schedule = sampleSchedule();
        schedule.setArchived(false);

        when(scheduleRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(schedule));

        scheduleService.archive(1L);

        assertThat(schedule.isArchived()).isTrue();
        verify(scheduleRepository).save(schedule);
    }

    @Test
    void shouldThrowWhenArchivingNonExistingSchedule() {
        when(scheduleRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> scheduleService.archive(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Schedule not found");
    }

    @Test
    void shouldUpdateSchedule() {
        Schedule existing = sampleSchedule();
        Schedule updated = sampleSchedule();

        updated.setGroup(new Group(2L, "Group B", false));
        updated.setSubject(new Subject(2L, "Physics", false));
        updated.setTeacher(new Teacher(2L, "Anna", "Kowalska", false));
        updated.setDayOfWeek("Wednesday");
        updated.setStartTime("10:00");
        updated.setEndTime("11:30");

        when(scheduleRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(existing));
        when(scheduleRepository.save(existing)).thenReturn(existing);

        Schedule result = scheduleService.update(1L, updated);

        assertThat(result.getGroup().getName()).isEqualTo("Group B");
        assertThat(result.getSubject().getName()).isEqualTo("Physics");
        assertThat(result.getTeacher().getFirstName()).isEqualTo("Anna");
        assertThat(result.getTeacher().getLastName()).isEqualTo("Kowalska");
        assertThat(result.getDayOfWeek()).isEqualTo("Wednesday");
        assertThat(result.getStartTime()).isEqualTo("10:00");
        assertThat(result.getEndTime()).isEqualTo("11:30");

        verify(scheduleRepository).save(existing);
    }
}
