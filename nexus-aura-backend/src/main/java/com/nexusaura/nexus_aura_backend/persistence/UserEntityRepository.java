package com.nexusaura.nexus_aura_backend.persistence;

import com.nexusaura.nexus_aura_backend.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByEmail(String email);
}
