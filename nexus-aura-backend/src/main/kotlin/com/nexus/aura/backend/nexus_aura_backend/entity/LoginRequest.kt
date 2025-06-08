package com.nexus.aura.backend.nexus_aura_backend.entity

data class LoginRequest(
    val identifier: String, // can be either email or username
    val password: String
)