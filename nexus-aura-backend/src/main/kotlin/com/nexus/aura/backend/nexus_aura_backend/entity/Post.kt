package com.nexus.aura.backend.nexus_aura_backend.entity

import java.util.*
import jakarta.persistence.*

@Entity
@Table(name = "posts")
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var content: String,

    @Column(nullable = false)
    val createdAt: Date = Date(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Column(nullable = false)
    var likes: Int = 0,
)
