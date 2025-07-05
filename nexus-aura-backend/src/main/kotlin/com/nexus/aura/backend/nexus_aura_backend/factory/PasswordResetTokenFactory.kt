package com.nexus.aura.backend.nexus_aura_backend.factory

import com.nexus.aura.backend.nexus_aura_backend.entity.PasswordResetToken
import com.nexus.aura.backend.nexus_aura_backend.entity.User
import java.time.LocalDateTime
import java.util.*

object PasswordResetTokenFactory : AbstractTokenFactory<PasswordResetToken>() {
    override fun generateTokenString(): String = UUID.randomUUID().toString()
    override fun buildToken(token: String, user: User, expiry: LocalDateTime): PasswordResetToken {
        return PasswordResetToken(
            token = token,
            user = user,
            expiryDate = expiry
        )
    }
}
