package com.example.demo.controller;

import com.example.demo.model.Group;
import com.example.demo.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping
    public Group create(@RequestBody Group group) {
        return groupService.create(group);
    }

    @GetMapping("/{id}")
    public Group getById(@PathVariable Long id) {
        return groupService.getById(id);
    }

    @GetMapping
    public List<Group> getAll() {
        return groupService.getAll();
    }

    @PutMapping("/{id}")
    public Group update(@PathVariable Long id, @RequestBody Group updatedGroup) {
        return groupService.update(id, updatedGroup);
    }

    @DeleteMapping("/{id}")
    public void archive(@PathVariable Long id) {
        groupService.archive(id);
    }
}
