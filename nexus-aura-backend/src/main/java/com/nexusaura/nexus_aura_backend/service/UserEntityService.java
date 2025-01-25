package com.nexusaura.nexus_aura_backend.service;

import com.nexusaura.nexus_aura_backend.dto.UserEntityDTO;
import com.nexusaura.nexus_aura_backend.model.UserEntity;
import com.nexusaura.nexus_aura_backend.persistence.UserEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEntityService {

    private final UserEntityRepository userEntityRepository;

    public UserEntityService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    public UserEntityDTO getUserById(Integer userId) {
        UserEntity userEntity = userEntityRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return mapToDTO(userEntity);
    }

    public UserEntityDTO createUser(UserEntityDTO userEntityDTO) {
        if (userEntityRepository.existsByEmail(userEntityDTO.getEmail())) {
            throw new RuntimeException("Email already exists: " + userEntityDTO.getEmail());
        }

        UserEntity userEntity = mapToEntity(userEntityDTO);
        UserEntity savedUser = userEntityRepository.save(userEntity);
        return mapToDTO(savedUser);
    }

    public List<UserEntityDTO> getAllUsers() {
        return userEntityRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public void deleteUserById(Integer userId) {
        if (!userEntityRepository.existsById(userId)) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
        userEntityRepository.deleteById(userId);
    }

    // Mapping methods
    private UserEntityDTO mapToDTO(UserEntity userEntity) {
        return UserEntityDTO.builder()
                .userID(userEntity.getUserID())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .bio(userEntity.getBio())
                .profilePicture(userEntity.getProfilePicture())
                .build();
    }

    private UserEntity mapToEntity(UserEntityDTO userEntityDTO) {
        return UserEntity.builder()
                .username(userEntityDTO.getUsername())
                .email(userEntityDTO.getEmail())
                .password(userEntityDTO.getPassword()) // Ideally, hashed
                .name(userEntityDTO.getName())
                .bio(userEntityDTO.getBio())
                .profilePicture(userEntityDTO.getProfilePicture())
                .build();
    }
}

