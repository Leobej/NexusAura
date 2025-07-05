package com.nexus.aura.backend.nexus_aura_backend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "users")
class User private constructor(
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
    val createdAt: Date = Date(),

    @Column(nullable = false)
    var isLocked: Boolean = false,

    @Column(nullable = false)
    var isDisabled: Boolean = false,

    @Column(nullable = false)
    var isEmailVerified: Boolean = false
) {
    data class Builder(
        var id: UUID = UUID.randomUUID(),
        var username: String = "",
        var email: String = "",
        var passwordHash: String = "",
        var fullName: String? = null,
        var bio: String? = null,
        var profilePictureUrl: String? = null,
        var createdAt: Date = Date(),
        var isLocked: Boolean = false,
        var isDisabled: Boolean = false,
        var isEmailVerified: Boolean = false
    ) {
        fun id(id: UUID) = apply { this.id = id }
        fun username(username: String) = apply { this.username = username }
        fun email(email: String) = apply { this.email = email }
        fun passwordHash(passwordHash: String) = apply { this.passwordHash = passwordHash }
        fun fullName(fullName: String?) = apply { this.fullName = fullName }
        fun bio(bio: String?) = apply { this.bio = bio }
        fun profilePictureUrl(profilePictureUrl: String?) = apply { this.profilePictureUrl = profilePictureUrl }
        fun createdAt(createdAt: Date) = apply { this.createdAt = createdAt }
        fun isLocked(isLocked: Boolean) = apply { this.isLocked = isLocked }
        fun isDisabled(isDisabled: Boolean) = apply { this.isDisabled = isDisabled }
        fun isEmailVerified(isEmailVerified: Boolean) = apply { this.isEmailVerified = isEmailVerified }
        fun build() = User(id, username, email, passwordHash, fullName, bio, profilePictureUrl, createdAt, isLocked, isDisabled, isEmailVerified)
    }
}
