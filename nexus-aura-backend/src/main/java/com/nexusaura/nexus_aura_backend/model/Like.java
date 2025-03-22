package com.nexusaura.nexus_aura_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Likes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer likeID;

    @ManyToOne
    @JoinColumn(name = "postID")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "commentID")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}

