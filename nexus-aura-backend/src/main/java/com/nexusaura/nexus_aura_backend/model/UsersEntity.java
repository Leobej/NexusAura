package com.nexusaura.nexus_aura_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usersEntity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 20)
    private String role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}

