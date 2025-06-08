package com.nexus.aura.backend.nexus_aura_backend.dto

data class ResetPasswordRequest(
    val token: String,
    val newPassword: String
)