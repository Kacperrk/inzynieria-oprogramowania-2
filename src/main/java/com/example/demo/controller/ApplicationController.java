package com.example.demo.controller;

import com.example.demo.model.Application;
import com.example.demo.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    public Application create(@RequestBody Application application) {
        return applicationService.create(application);
    }

    @GetMapping("/{id}")
    public Application getById(@PathVariable Long id) {
        return applicationService.getById(id);
    }

    @GetMapping
    public List<Application> getAll() {
        return applicationService.getAll();
    }

    @PutMapping("/{id}")
    public Application update(@PathVariable Long id, @RequestBody Application updatedApplication) {
        return applicationService.update(id, updatedApplication);
    }

    @DeleteMapping("/{id}")
    public void archive(@PathVariable Long id) {
        applicationService.archive(id);
    }
}