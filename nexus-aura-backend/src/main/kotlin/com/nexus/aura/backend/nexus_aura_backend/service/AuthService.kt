package com.nexus.aura.backend.nexus_aura_backend.service

import com.nexus.aura.backend.nexus_aura_backend.entity.PasswordResetToken
import com.nexus.aura.backend.nexus_aura_backend.exception.InvalidTokenException
import com.nexus.aura.backend.nexus_aura_backend.exception.TokenExpiredException
import com.nexus.aura.backend.nexus_aura_backend.repository.PasswordResetTokenRepository
import com.nexus.aura.backend.nexus_aura_backend.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class AuthService(
    private val passwordResetTokenRepository: PasswordResetTokenRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val emailService: EmailService
) {
    fun initiatePasswordReset(email: String) {
        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("User with email $email not found")

        val token = UUID.randomUUID().toString()
        val expiryDate = LocalDateTime.now().plusHours(1) // Token valid for 1 hour

        val passwordResetToken = PasswordResetToken(
            token = token,
            user = user,
            expiryDate = expiryDate
        )

        passwordResetTokenRepository.save(passwordResetToken)

        val resetLink = "https://yourdomain.com/reset-password?token=$token"
        emailService.sendPasswordResetEmail(user.email, resetLink)
    }


    fun resetPassword(token: String, newPassword: String) {
        val tokenEntity = passwordResetTokenRepository.findByToken(token)
            ?: throw InvalidTokenException("Invalid token")

        if (tokenEntity.expiryDate.isBefore(LocalDateTime.now())) {
            throw TokenExpiredException("Token expired")
        }

        val user = tokenEntity.user
        user.passwordHash = passwordEncoder.encode(newPassword)
        userRepository.save(user)
        passwordResetTokenRepository.delete(tokenEntity)
    }
}