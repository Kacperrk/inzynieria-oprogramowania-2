package com.example.demo.controller;

import com.example.demo.model.Subject;
import com.example.demo.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping
    public Subject create(@RequestBody Subject subject) {
        return subjectService.create(subject);
    }

    @GetMapping("/{id}")
    public Subject getById(@PathVariable Long id) {
        return subjectService.getById(id);
    }

    @GetMapping
    public List<Subject> getAll() {
        return subjectService.getAll();
    }

    @PutMapping("/{id}")
    public Subject update(@PathVariable Long id, @RequestBody Subject updatedSubject) {
        return subjectService.update(id, updatedSubject);
    }

    @DeleteMapping("/{id}")
    public void archive(@PathVariable Long id) {
        subjectService.archive(id);
    }
}
