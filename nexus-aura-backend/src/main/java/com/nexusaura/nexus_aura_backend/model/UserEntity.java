package com.nexusaura.nexus_aura_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "userEntity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(length = 255)
    private String profilePicture;
}

