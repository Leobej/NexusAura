package com.nexus.aura.backend.nexus_aura_backend.dto

import jakarta.validation.constraints.Size

data class UserProfileUpdateRequest(
    @field:Size(max = 100, message = "Full name must be at most 100 characters")
    val fullName: String? = null,

    @field:Size(max = 255, message = "Bio must be at most 255 characters")
    val bio: String? = null,

    @field:Size(max = 500, message = "Profile picture URL must be at most 500 characters")
    val profilePictureUrl: String? = null
)

