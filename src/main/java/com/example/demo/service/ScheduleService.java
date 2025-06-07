package com.example.demo.service;

import com.example.demo.model.Schedule;
import com.example.demo.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public Schedule create(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public Schedule getById(Long id) {
        return scheduleRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));
    }

    public List<Schedule> getAll() {
        return scheduleRepository.findAllByArchivedFalse();
    }

    public void archive(Long id) {
        Schedule schedule = getById(id);
        schedule.setArchived(true);
        scheduleRepository.save(schedule);
    }

    public Schedule update(Long id, Schedule updatedSchedule) {
        Schedule schedule = getById(id);
        schedule.setGroup(updatedSchedule.getGroup());
        schedule.setSubject(updatedSchedule.getSubject());
        schedule.setTeacher(updatedSchedule.getTeacher());
        schedule.setDayOfWeek(updatedSchedule.getDayOfWeek());
        schedule.setStartTime(updatedSchedule.getStartTime());
        schedule.setEndTime(updatedSchedule.getEndTime());
        return scheduleRepository.save(schedule);
    }
}