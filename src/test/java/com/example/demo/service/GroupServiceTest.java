package com.example.demo.service;

import com.example.demo.model.Group;
import com.example.demo.repository.GroupRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class GroupServiceTest {

    private GroupRepository groupRepository;
    private GroupService groupService;

    @BeforeEach
    void setUp() {
        groupRepository = mock(GroupRepository.class);
        groupService = new GroupService(groupRepository);
    }

    @Test
    void shouldCreateGroup() {
        Group group = new Group();
        group.setName("Group A");

        when(groupRepository.save(group)).thenReturn(group);

        Group result = groupService.create(group);

        assertThat(result).isEqualTo(group);
        verify(groupRepository).save(group);
    }

    @Test
    void shouldReturnGroupById() {
        Group group = new Group();
        group.setId(1L);

        when(groupRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(group));

        Group result = groupService.getById(1L);

        assertThat(result).isEqualTo(group);
    }

    @Test
    void shouldThrowWhenGroupNotFoundById() {
        when(groupRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> groupService.getById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Group not found");
    }

    @Test
    void shouldReturnAllGroups() {
        List<Group> groups = List.of(new Group(), new Group());
        when(groupRepository.findAllByArchivedFalse()).thenReturn(groups);

        List<Group> result = groupService.getAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldArchiveGroup() {
        Group group = new Group();
        group.setArchived(false);

        when(groupRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(group));

        groupService.archive(1L);

        assertThat(group.isArchived()).isTrue();
        verify(groupRepository).save(group);
    }

    @Test
    void shouldThrowWhenArchivingNonExistingGroup() {
        when(groupRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> groupService.archive(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Group not found");
    }

    @Test
    void shouldUpdateGroup() {
        Group existingGroup = new Group();
        existingGroup.setId(1L);
        existingGroup.setName("Old Name");

        Group updatedGroup = new Group();
        updatedGroup.setName("New Name");

        when(groupRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(existingGroup));
        when(groupRepository.save(existingGroup)).thenReturn(existingGroup);

        Group result = groupService.update(1L, updatedGroup);

        assertThat(result.getName()).isEqualTo("New Name");
        verify(groupRepository).save(existingGroup);
    }
}
