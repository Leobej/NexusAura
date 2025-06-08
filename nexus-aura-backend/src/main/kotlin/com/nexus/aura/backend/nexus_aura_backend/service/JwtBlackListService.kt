package com.nexus.aura.backend.nexus_aura_backend.service

import com.nexus.aura.backend.nexus_aura_backend.util.JwtUtil
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Service
class JwtBlacklistService(
    private val jwtUtil: JwtUtil
) {
    private val blacklist = ConcurrentHashMap<String, Date>() // token -> expiry time

    fun blacklistToken(token: String) {
        val expiration = jwtUtil.getExpirationDate(token)
        blacklist[token] = expiration
    }

    fun isTokenBlacklisted(token: String): Boolean {
        val expiration = blacklist[token] ?: return false
        return expiration.after(Date())
    }

    @Scheduled(fixedDelay = 3600000) // every hour
    fun cleanupExpiredTokens() {
        val now = Date()
        blacklist.entries.removeIf { it.value.before(now) }
    }
}