package com.nexus.aura.backend.nexus_aura_backend.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*

interface IJwtBlacklistService {
    fun blacklistToken(token: String)
    fun isTokenBlacklisted(token: String): Boolean
}

@Service
class JwtBlacklistService : IJwtBlacklistService {
    private val blacklist = Collections.synchronizedSet(mutableSetOf<String>())

    override fun blacklistToken(token: String) {
        blacklist.add(token)
    }

    override fun isTokenBlacklisted(token: String): Boolean {
        return blacklist.contains(token)
    }

    @Scheduled(fixedDelay = 3600000)
    fun cleanupExpiredTokens() {
    }
}