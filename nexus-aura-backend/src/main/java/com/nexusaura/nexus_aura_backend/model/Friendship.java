package com.nexusaura.nexus_aura_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Friendship")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer friendshipID;

    @ManyToOne
    @JoinColumn(name = "userID1", nullable = false)
    private UserEntity user1;

    @ManyToOne
    @JoinColumn(name = "userID2", nullable = false)
    private UserEntity user2;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
