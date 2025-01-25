package com.nexusaura.nexus_aura_backend.service;

import com.nexusaura.nexus_aura_backend.dto.UsersEntityDTO;
import com.nexusaura.nexus_aura_backend.model.UsersEntity;
import com.nexusaura.nexus_aura_backend.persistence.UsersEntityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsersEntityService {

    private final UsersEntityRepository usersEntityRepository;

    public UsersEntityService(UsersEntityRepository usersEntityRepository) {
        this.usersEntityRepository = usersEntityRepository;
    }

    public UsersEntityDTO getUserById(Integer id) {
        UsersEntity usersEntity = usersEntityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        return mapToDTO(usersEntity);
    }

    public UsersEntityDTO createUser(UsersEntityDTO usersEntityDTO) {
        if (usersEntityRepository.existsByUsername(usersEntityDTO.getUsername())) {
            throw new RuntimeException("Username already exists: " + usersEntityDTO.getUsername());
        }

        UsersEntity usersEntity = mapToEntity(usersEntityDTO);
        usersEntity.setCreatedAt(LocalDateTime.now()); // Set creation timestamp
        UsersEntity savedUser = usersEntityRepository.save(usersEntity);
        return mapToDTO(savedUser);
    }

    public List<UsersEntityDTO> getAllUsers() {
        return usersEntityRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public void deleteUserById(Integer id) {
        if (!usersEntityRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        usersEntityRepository.deleteById(id);
    }

    public UsersEntityDTO updateUserRole(Integer id, String newRole) {
        UsersEntity usersEntity = usersEntityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        usersEntity.setRole(newRole);
        UsersEntity updatedUser = usersEntityRepository.save(usersEntity);
        return mapToDTO(updatedUser);
    }

    // Mapping methods
    private UsersEntityDTO mapToDTO(UsersEntity usersEntity) {
        return UsersEntityDTO.builder()
                .id(usersEntity.getId())
                .username(usersEntity.getUsername())
                .role(usersEntity.getRole())
                .createdAt(usersEntity.getCreatedAt().toString())
                .build();
    }

    private UsersEntity mapToEntity(UsersEntityDTO usersEntityDTO) {
        return UsersEntity.builder()
                .id(usersEntityDTO.getId())
                .username(usersEntityDTO.getUsername())
                .role(usersEntityDTO.getRole())
                .build();
    }
}

