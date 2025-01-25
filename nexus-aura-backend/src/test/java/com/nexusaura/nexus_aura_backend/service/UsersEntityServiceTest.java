package com.nexusaura.nexus_aura_backend.service;

import com.nexusaura.nexus_aura_backend.dto.UsersEntityDTO;
import com.nexusaura.nexus_aura_backend.model.UsersEntity;
import com.nexusaura.nexus_aura_backend.persistence.UsersEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersEntityServiceTest {

    @Mock
    private UsersEntityRepository usersEntityRepository;

    @InjectMocks
    private UsersEntityService usersEntityService;

    @Test
    void createUser_shouldReturnCreatedUserDTO_whenValidRequest() {
        // Arrange
        UsersEntityDTO requestDTO = UsersEntityDTO.builder()
                .username("testUser")
                .role("USER")
                .build();

        UsersEntity mockEntity = UsersEntity.builder()
                .id(1)
                .username("testUser")
                .role("USER")
                .createdAt(LocalDateTime.now())
                .build();

        when(usersEntityRepository.save(any(UsersEntity.class))).thenReturn(mockEntity);

        // Act
        UsersEntityDTO result = usersEntityService.createUser(requestDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getUsername()).isEqualTo("testUser");
        verify(usersEntityRepository, times(1)).save(any(UsersEntity.class));
    }

    @Test
    void createUser_shouldThrowException_whenUsernameAlreadyExists() {
        // Arrange
        UsersEntityDTO requestDTO = UsersEntityDTO.builder()
                .username("existingUser")
                .role("USER")
                .build();

        when(usersEntityRepository.existsByUsername("existingUser")).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> usersEntityService.createUser(requestDTO));
        verify(usersEntityRepository, never()).save(any(UsersEntity.class));
    }
}
