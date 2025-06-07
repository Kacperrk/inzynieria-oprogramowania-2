package com.example.demo.controller;

import com.example.demo.model.Parent;
import com.example.demo.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
@RequiredArgsConstructor
public class ParentController {
    private final ParentService parentService;

    @PostMapping
    public Parent create(@RequestBody Parent parent) {
        return parentService.create(parent);
    }

    @GetMapping("/{id}")
    public Parent getById(@PathVariable Long id) {
        return parentService.getById(id);
    }

    @GetMapping
    public List<Parent> getAll() {
        return parentService.getAll();
    }

    @PutMapping("/{id}")
    public Parent update(@PathVariable Long id, @RequestBody Parent updatedParent) {
        return parentService.update(id, updatedParent);
    }

    @DeleteMapping("/{id}")
    public void archive(@PathVariable Long id) {
        parentService.archive(id);
    }
}