package com.nexusaura.nexus_aura_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentID;

    @ManyToOne
    @JoinColumn(name = "postID", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private UserEntity user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}

