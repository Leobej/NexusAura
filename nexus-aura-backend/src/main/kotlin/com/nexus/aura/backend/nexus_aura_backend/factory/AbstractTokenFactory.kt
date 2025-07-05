package com.nexus.aura.backend.nexus_aura_backend.factory

import com.nexus.aura.backend.nexus_aura_backend.entity.User
import java.time.LocalDateTime

abstract class AbstractTokenFactory<T> {
    fun createToken(user: User, expiryHours: Long = 1): T {
        val token = generateTokenString()
        val expiry = LocalDateTime.now().plusHours(expiryHours)
        return buildToken(token, user, expiry)
    }
    protected abstract fun generateTokenString(): String
    protected abstract fun buildToken(token: String, user: User, expiry: LocalDateTime): T
}

