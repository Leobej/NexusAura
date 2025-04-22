package com.nexus.aura.backend.nexus_aura_backend.dto

import java.util.*


data class UserResponse(
    val id: UUID,
    val userName: String,
    val email: String,
    val fullName: String?,
    val bio: String?,
    val profilePictureUrl: String?,
    val createdAt: Date
)
