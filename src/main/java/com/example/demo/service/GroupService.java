package com.example.demo.service;

import com.example.demo.model.Group;
import com.example.demo.repository.GroupRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    public Group create(Group group) {
        return groupRepository.save(group);
    }

    public Group getById(Long id) {
        return groupRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));
    }

    public List<Group> getAll() {
        return groupRepository.findAllByArchivedFalse();
    }

    public void archive(Long id) {
        Group group = getById(id);
        group.setArchived(true);
        groupRepository.save(group);
    }

    public Group update(Long id, Group updatedGroup) {
        Group group = getById(id);
        group.setName(updatedGroup.getName());
        return groupRepository.save(group);
    }
}
