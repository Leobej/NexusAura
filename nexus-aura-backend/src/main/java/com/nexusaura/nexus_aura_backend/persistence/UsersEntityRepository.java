package com.nexusaura.nexus_aura_backend.persistence;

import com.nexusaura.nexus_aura_backend.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersEntityRepository extends JpaRepository<UsersEntity, Integer> {
    Optional<UsersEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}
