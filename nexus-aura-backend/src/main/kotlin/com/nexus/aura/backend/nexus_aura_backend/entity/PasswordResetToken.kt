package com.nexus.aura.backend.nexus_aura_backend.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
data class PasswordResetToken(
    @Id
    val token: String = UUID.randomUUID().toString(),

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    val user: User,

    val expiryDate: LocalDateTime = LocalDateTime.now().plusHours(1),

    @Column(nullable = false)
    var isUsed: Boolean = false
)