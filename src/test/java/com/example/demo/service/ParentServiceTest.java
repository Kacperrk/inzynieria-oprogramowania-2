package com.example.demo.service;

import com.example.demo.model.Parent;
import com.example.demo.repository.ParentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParentServiceTest {

    private ParentRepository parentRepository;
    private ParentService parentService;

    @BeforeEach
    void setUp() {
        parentRepository = mock(ParentRepository.class);
        parentService = new ParentService(parentRepository);
    }

    private Parent sampleParent() {
        Parent parent = new Parent();
        parent.setId(1L);
        parent.setFirstName("Jan");
        parent.setLastName("Nowak");
        parent.setEmail("jan.nowak@example.com");
        parent.setPhone("123456789");
        parent.setAddress("ul. Przykładowa 1, Warszawa");
        parent.setArchived(false);
        return parent;
    }

    @Test
    void shouldCreateParent() {
        Parent parent = sampleParent();

        when(parentRepository.save(parent)).thenReturn(parent);

        Parent result = parentService.create(parent);

        assertThat(result).isEqualTo(parent);
        verify(parentRepository).save(parent);
    }

    @Test
    void shouldReturnParentById() {
        Parent parent = sampleParent();

        when(parentRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(parent));

        Parent result = parentService.getById(1L);

        assertThat(result).isEqualTo(parent);
    }

    @Test
    void shouldThrowWhenParentNotFound() {
        when(parentRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> parentService.getById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Parent not found");
    }

    @Test
    void shouldReturnAllParents() {
        List<Parent> parents = List.of(sampleParent(), sampleParent());

        when(parentRepository.findAllByArchivedFalse()).thenReturn(parents);

        List<Parent> result = parentService.getAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getFirstName()).isEqualTo("Jan");
    }

    @Test
    void shouldArchiveParent() {
        Parent parent = sampleParent();
        parent.setArchived(false);

        when(parentRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(parent));

        parentService.archive(1L);

        assertThat(parent.isArchived()).isTrue();
        verify(parentRepository).save(parent);
    }

    @Test
    void shouldThrowWhenArchivingNonExistingParent() {
        when(parentRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> parentService.archive(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Parent not found");
    }

    @Test
    void shouldUpdateParent() {
        Parent existing = sampleParent();
        Parent updated = new Parent();
        updated.setFirstName("Anna");
        updated.setLastName("Kowalska");
        updated.setEmail("anna.kowalska@example.com");
        updated.setPhone("987654321");
        updated.setAddress("ul. Nowa 5, Kraków");

        when(parentRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(existing));
        when(parentRepository.save(existing)).thenReturn(existing);

        Parent result = parentService.update(1L, updated);

        assertThat(result.getFirstName()).isEqualTo("Anna");
        assertThat(result.getLastName()).isEqualTo("Kowalska");
        assertThat(result.getEmail()).isEqualTo("anna.kowalska@example.com");
        assertThat(result.getPhone()).isEqualTo("987654321");
        assertThat(result.getAddress()).isEqualTo("ul. Nowa 5, Kraków");

        verify(parentRepository).save(existing);
    }
}