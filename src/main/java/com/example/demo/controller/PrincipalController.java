package com.example.demo.controller;

import com.example.demo.model.Principal;
import com.example.demo.service.PrincipalService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/principals")
@RequiredArgsConstructor
public class PrincipalController {
    private final PrincipalService principalService;

    @PostMapping
    public Principal create(@RequestBody Principal principal) {
        return principalService.create(principal);
    }
    
    @GetMapping("/{id}")
    public Principal getById(@PathVariable Long id) {
        return principalService.getById(id);
    }

    @GetMapping
    public List<Principal> getAll() {
        return principalService.getAll();
    }

    @PutMapping("/{id}")
    public Principal update(@PathVariable Long id, @RequestBody Principal updatedPrincipal) {
        return principalService.update(id, updatedPrincipal);
    }

    @DeleteMapping("/{id}")
    public void archive(@PathVariable Long id) {
        principalService.archive(id);
    }
}
