package com.nexusaura.nexus_aura_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messageID;

    @ManyToOne
    @JoinColumn(name = "senderID", nullable = false)
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiverID", nullable = false)
    private UserEntity receiver;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}

