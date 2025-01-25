package com.nexusaura.nexus_aura_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "follows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Follows {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "following_user_id", nullable = false)
    private UserEntity followingUser;

    @ManyToOne
    @JoinColumn(name = "followed_user_id", nullable = false)
    private UserEntity followedUser;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}

