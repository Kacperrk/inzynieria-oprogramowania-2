package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @GetMapping("/{id}")
    public Student getById(@PathVariable Long id) {
        return studentService.getById(id);
    }

    @GetMapping
    public List<Student> getAll() {
        return studentService.getAll();
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @RequestBody Student updatedStudent) {
        return studentService.update(id, updatedStudent);
    }

    @DeleteMapping("/{id}")
    public void archive(@PathVariable Long id) {
        studentService.archive(id);
    }
}
