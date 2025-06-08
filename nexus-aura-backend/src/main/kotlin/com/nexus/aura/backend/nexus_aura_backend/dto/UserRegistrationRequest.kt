package com.nexus.aura.backend.nexus_aura_backend.dto
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull

data class UserRegistrationRequest(
    @field:NotEmpty(message = "Username must not be empty")
    val userName: String,

    @field:Email(message = "Invalid email format")
    @field:NotNull(value = "Email must not be null")
    val email: String,

    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    @field:NotNull(value = "Password must not be null")
    val password: String,

    val fullName: String? = null,
    val bio: String? = null,
    val profilePictureUrl: String? = null
)
