package com.example.demo.controller;

import com.example.demo.model.Teacher;
import com.example.demo.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping
    public Teacher create(@RequestBody Teacher teacher) {
        return teacherService.create(teacher);
    }

    @GetMapping("/{id}")
    public Teacher getById(@PathVariable Long id) {
        return teacherService.getById(id);
    }

    @GetMapping
    public List<Teacher> getAll() {
        return teacherService.getAll();
    }

    @PutMapping("/{id}")
    public Teacher update(@PathVariable Long id, @RequestBody Teacher updatedTeacher) {
        return teacherService.update(id, updatedTeacher);
    }

    @DeleteMapping("/{id}")
    public void archive(@PathVariable Long id) {
        teacherService.archive(id);
    }
}
