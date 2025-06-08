package com.nexus.aura.backend.nexus_aura_backend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "users")
class User(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(unique = true, nullable = false)
    val username: String,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false)
    var passwordHash: String,

    var fullName: String? = null,
    var bio: String? = null,
    var profilePictureUrl: String? = null,

    @Column(nullable = false)
    val createdAt: Date = Date()

)