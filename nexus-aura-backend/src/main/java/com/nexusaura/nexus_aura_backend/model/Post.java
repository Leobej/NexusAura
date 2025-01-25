package com.nexusaura.nexus_aura_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postID;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private UserEntity user;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 20)
    private String mediaType;

    @Column(length = 255)
    private String mediaURL;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
