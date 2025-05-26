package com.example.demo.controller;

import com.example.demo.model.Grade;
import com.example.demo.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
public class GradeController {
    private final GradeService gradeService;

    @PostMapping
    public Grade create(@RequestBody Grade grade) {
        return gradeService.create(grade);
    }

    @GetMapping("/{id}")
    public Grade getById(@PathVariable Long id) {
        return gradeService.getById(id);
    }

    @GetMapping
    public List<Grade> getAll() {
        return gradeService.getAll();
    }

    @PutMapping("/{id}")
    public Grade update(@PathVariable Long id, @RequestBody Grade updatedGrade) {
        return gradeService.update(id, updatedGrade);
    }

    @DeleteMapping("/{id}")
    public void archive(@PathVariable Long id) {
        gradeService.archive(id);
    }
}
