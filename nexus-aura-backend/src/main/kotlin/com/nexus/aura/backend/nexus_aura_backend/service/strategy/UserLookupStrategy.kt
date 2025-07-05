package com.nexus.aura.backend.nexus_aura_backend.service.strategy

import com.nexus.aura.backend.nexus_aura_backend.entity.User
import com.nexus.aura.backend.nexus_aura_backend.repository.UserRepository

interface UserLookupStrategy {
    fun findUser(identifier: String): User?
}

class EmailLookupStrategy(private val userRepository: UserRepository) : UserLookupStrategy {
    override fun findUser(identifier: String): User? = userRepository.findByEmail(identifier)
}

class UsernameLookupStrategy(private val userRepository: UserRepository) : UserLookupStrategy {
    override fun findUser(identifier: String): User? = userRepository.findByUsername(identifier)
}

class UserLookupContext(private val strategies: List<UserLookupStrategy>) {
    fun findUser(identifier: String): User? {
        return strategies.firstNotNullOfOrNull { it.findUser(identifier) }
    }
}

