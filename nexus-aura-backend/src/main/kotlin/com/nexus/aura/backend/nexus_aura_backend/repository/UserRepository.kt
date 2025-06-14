package com.nexus.aura.backend.nexus_aura_backend.repository

import com.nexus.aura.backend.nexus_aura_backend.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {
    fun existsByEmail(email: String): Boolean
    fun existsByUsername(username: String): Boolean
    fun findByEmail(email: String): User?
    fun findByUsername(username: String): User?
}