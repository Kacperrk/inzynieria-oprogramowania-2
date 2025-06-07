package com.example.demo.controller;

import com.example.demo.model.Schedule;
import com.example.demo.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public Schedule create(@RequestBody Schedule schedule) {
        return scheduleService.create(schedule);
    }

    @GetMapping("/{id}")
    public Schedule getById(@PathVariable Long id) {
        return scheduleService.getById(id);
    }

    @GetMapping
    public List<Schedule> getAll() {
        return scheduleService.getAll();
    }

    @PutMapping("/{id}")
    public Schedule update(@PathVariable Long id, @RequestBody Schedule updatedSchedule) {
        return scheduleService.update(id, updatedSchedule);
    }

    @DeleteMapping("/{id}")
    public void archive(@PathVariable Long id) {
        scheduleService.archive(id);
    }
}