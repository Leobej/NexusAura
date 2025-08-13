package com.nexus.aura.backend.nexus_aura_backend.dto

import jakarta.validation.constraints.NotBlank

// DTO for creating a new post
class CreatePostRequest(
    @field:NotBlank
    val content: String
)
