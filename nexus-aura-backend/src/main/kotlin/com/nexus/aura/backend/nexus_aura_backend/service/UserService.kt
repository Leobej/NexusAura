package com.nexus.aura.backend.nexus_aura_backend.service

import com.nexus.aura.backend.nexus_aura_backend.dto.UserRegistrationRequest
import com.nexus.aura.backend.nexus_aura_backend.dto.UserResponse
import com.nexus.aura.backend.nexus_aura_backend.entity.User
import com.nexus.aura.backend.nexus_aura_backend.entity.UserProfileUpdateRequest
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

    fun updateUserProfile(email: String, request: UserProfileUpdateRequest): UserResponse {
        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("User not found")

        request.fullName?.let { user.fullName = it }
        request.bio?.let { user.bio = it }
        request.profilePictureUrl?.let { user.profilePictureUrl = it }

        val saved = userRepository.save(user)

        return UserResponse(
            id = saved.id,
            userName = saved.username,
            email = saved.email,
            fullName = saved.fullName,
            bio = saved.bio,
            profilePictureUrl = saved.profilePictureUrl,
            createdAt = saved.createdAt
        )
    }

    /**
     * Finds a user by either their email or username.
     * If the identifier contains an '@', it is treated as an email.
     */

    internal fun findUserByIdentifier(identifier: String): User? {
        return if (identifier.contains("@")) {
            userRepository.findByEmail(identifier)
        } else {
            userRepository.findByUsername(identifier)
        }
    }

}