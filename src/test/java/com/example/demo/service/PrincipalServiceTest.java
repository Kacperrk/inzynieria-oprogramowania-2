package com.example.demo.service;

import com.example.demo.model.Principal;
import com.example.demo.repository.PrincipalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PrincipalServiceTest {

    private PrincipalRepository principalRepository;
    private PrincipalService principalService;

    @BeforeEach
    void setUp() {
        principalRepository = mock(PrincipalRepository.class);
        principalService = new PrincipalService(principalRepository);
    }

    private Principal samplePrincipal() {
        Principal principal = new Principal();
        principal.setId(1L);
        principal.setFirstName("Jan");
        principal.setLastName("Nowak");
        principal.setEmail("jan.nowak@example.com");
        principal.setPhone("123456789");
        principal.setArchived(false);
        return principal;
    }

    @Test
    void shouldCreatePrincipal() {
        Principal principal = samplePrincipal();

        when(principalRepository.save(principal)).thenReturn(principal);

        Principal result = principalService.create(principal);

        assertThat(result).isEqualTo(principal);
        verify(principalRepository).save(principal);
    }

    @Test
    void shouldReturnPrincipalById() {
        Principal principal = samplePrincipal();

        when(principalRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(principal));

        Principal result = principalService.getById(1L);

        assertThat(result).isEqualTo(principal);
    }

    @Test
    void shouldThrowWhenPrincipalNotFound() {
        when(principalRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> principalService.getById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Principal not found");
    }

    @Test
    void shouldReturnAllPrincipals() {
        List<Principal> principals = List.of(samplePrincipal(), samplePrincipal());

        when(principalRepository.findAllByArchivedFalse()).thenReturn(principals);

        List<Principal> result = principalService.getAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getFirstName()).isEqualTo("Jan");
    }

    @Test
    void shouldArchivePrincipal() {
        Principal principal = samplePrincipal();
        principal.setArchived(false);

        when(principalRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(principal));

        principalService.archive(1L);

        assertThat(principal.isArchived()).isTrue();
        verify(principalRepository).save(principal);
    }

    @Test
    void shouldThrowWhenArchivingNonExistingPrincipal() {
        when(principalRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> principalService.archive(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Principal not found");
    }

    @Test
    void shouldUpdatePrincipal() {
        Principal existing = samplePrincipal();
        Principal updated = new Principal();
        updated.setFirstName("Anna");
        updated.setLastName("Kowalska");
        updated.setEmail("anna.kowalska@example.com");
        updated.setPhone("987654321");

        when(principalRepository.findByIdAndArchivedFalse(1L)).thenReturn(Optional.of(existing));
        when(principalRepository.save(existing)).thenReturn(existing);

        Principal result = principalService.update(1L, updated);

        assertThat(result.getFirstName()).isEqualTo("Anna");
        assertThat(result.getLastName()).isEqualTo("Kowalska");
        assertThat(result.getEmail()).isEqualTo("anna.kowalska@example.com");
        assertThat(result.getPhone()).isEqualTo("987654321");

        verify(principalRepository).save(existing);
    }
}