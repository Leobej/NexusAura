package com.nexus.aura.backend.nexus_aura_backend.service

import com.nexus.aura.backend.nexus_aura_backend.dto.UserRegistrationRequest
import com.nexus.aura.backend.nexus_aura_backend.dto.UserResponse
import com.nexus.aura.backend.nexus_aura_backend.entity.User
import com.nexus.aura.backend.nexus_aura_backend.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun registerUser(request: UserRegistrationRequest): UserResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email is already in use")
        }
        if (userRepository.existsByUsername(request.userName)) {
            throw IllegalArgumentException("Username is already taken")
        }

        val user = User(
            id = UUID.randomUUID(),
            username = request.userName,
            email = request.email,
            passwordHash = passwordEncoder.encode(request.password),
            fullName = request.fullName,
            bio = request.bio,
            profilePictureUrl = request.profilePictureUrl
        )

        val savedUser = userRepository.save(user)
        return UserResponse(
            id = savedUser.id,
            userName = savedUser.username,
            email = savedUser.email,
            fullName = savedUser.fullName,
            bio = savedUser.bio,
            profilePictureUrl = savedUser.profilePictureUrl,
            createdAt = savedUser.createdAt
        )
    }
}