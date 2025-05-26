package com.example.demo.controller;

import com.example.demo.model.Lesson;
import com.example.demo.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @PostMapping
    public Lesson create(@RequestBody Lesson lesson) {
        return lessonService.create(lesson);
    }

    @GetMapping("/{id}")
    public Lesson getById(@PathVariable Long id) {
        return lessonService.getById(id);
    }

    @GetMapping
    public List<Lesson> getAll() {
        return lessonService.getAll();
    }

    @PutMapping("/{id}")
    public Lesson update(@PathVariable Long id, @RequestBody Lesson updatedLesson) {
        return lessonService.update(id, updatedLesson);
    }

    @DeleteMapping("/{id}")
    public void archive(@PathVariable Long id) {
        lessonService.archive(id);
    }
}
