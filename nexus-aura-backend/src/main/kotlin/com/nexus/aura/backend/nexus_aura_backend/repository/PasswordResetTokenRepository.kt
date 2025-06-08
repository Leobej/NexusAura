package com.nexus.aura.backend.nexus_aura_backend.repository

import com.nexus.aura.backend.nexus_aura_backend.entity.PasswordResetToken
import org.springframework.data.jpa.repository.JpaRepository

interface PasswordResetTokenRepository : JpaRepository<PasswordResetToken, String> {
    fun findByToken(token: String): PasswordResetToken?
}