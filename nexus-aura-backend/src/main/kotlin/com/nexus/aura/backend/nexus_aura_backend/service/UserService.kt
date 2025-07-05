package com.nexus.aura.backend.nexus_aura_backend.service

import com.nexus.aura.backend.nexus_aura_backend.dto.UserProfileUpdateRequest
import com.nexus.aura.backend.nexus_aura_backend.dto.UserRegistrationRequest
import com.nexus.aura.backend.nexus_aura_backend.dto.UserResponse
import com.nexus.aura.backend.nexus_aura_backend.entity.User
import com.nexus.aura.backend.nexus_aura_backend.event.UserRegisteredEvent
import com.nexus.aura.backend.nexus_aura_backend.exception.UserAlreadyExistsException
import com.nexus.aura.backend.nexus_aura_backend.exception.UserNotFoundException
import com.nexus.aura.backend.nexus_aura_backend.exception.UsernameAlreadyTakenException
import com.nexus.aura.backend.nexus_aura_backend.repository.UserRepository
import com.nexus.aura.backend.nexus_aura_backend.service.strategy.EmailLookupStrategy
import com.nexus.aura.backend.nexus_aura_backend.service.strategy.UserLookupContext
import com.nexus.aura.backend.nexus_aura_backend.service.strategy.UsernameLookupStrategy
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

interface IUserService {
    fun registerUser(request: UserRegistrationRequest): UserResponse
    fun updateUserProfile(email: String, request: UserProfileUpdateRequest): UserResponse
    fun findUserByIdentifier(identifier: String): User?
}

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val eventPublisher: ApplicationEventPublisher
) : IUserService {
    private val userLookupContext = UserLookupContext(
        listOf(
            EmailLookupStrategy(userRepository),
            UsernameLookupStrategy(userRepository)
        )
    )

    override fun registerUser(request: UserRegistrationRequest): UserResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw UserAlreadyExistsException("Email is already in use")
        }
        if (userRepository.existsByUsername(request.username)) {
            throw UsernameAlreadyTakenException("Username is already taken")
        }

        val user = User.Builder()
            .username(request.username)
            .email(request.email)
            .passwordHash(passwordEncoder.encode(request.password))
            .fullName(request.fullName)
            .bio(request.bio)
            .profilePictureUrl(request.profilePictureUrl)
            .isLocked(false)
            .isDisabled(false)
            .isEmailVerified(false)
            .build()

        val savedUser = userRepository.save(user)
        eventPublisher.publishEvent(UserRegisteredEvent(savedUser))
        return UserResponse(
            id = savedUser.id,
            username = savedUser.username,
            email = savedUser.email,
            fullName = savedUser.fullName,
            bio = savedUser.bio,
            profilePictureUrl = savedUser.profilePictureUrl,
            createdAt = savedUser.createdAt
        )
    }

    override fun updateUserProfile(email: String, request: UserProfileUpdateRequest): UserResponse {
        val user = userRepository.findByEmail(email)
            ?: throw UserNotFoundException("User not found")

        request.fullName?.let { user.fullName = it }
        request.bio?.let { user.bio = it }
        request.profilePictureUrl?.let { user.profilePictureUrl = it }

        val saved = userRepository.save(user)

        return UserResponse(
            id = saved.id,
            username = saved.username,
            email = saved.email,
            fullName = saved.fullName,
            bio = saved.bio,
            profilePictureUrl = saved.profilePictureUrl,
            createdAt = saved.createdAt
        )
    }

    override fun findUserByIdentifier(identifier: String): User? {
        return userLookupContext.findUser(identifier)
    }
}