package com.nexus.aura.backend.nexus_aura_backend.service

import com.nexus.aura.backend.nexus_aura_backend.entity.PasswordResetToken
import com.nexus.aura.backend.nexus_aura_backend.event.PasswordResetRequestedEvent
import com.nexus.aura.backend.nexus_aura_backend.exception.*
import com.nexus.aura.backend.nexus_aura_backend.factory.PasswordResetTokenFactory
import com.nexus.aura.backend.nexus_aura_backend.repository.PasswordResetTokenRepository
import com.nexus.aura.backend.nexus_aura_backend.repository.UserRepository
import com.nexus.aura.backend.nexus_aura_backend.service.email.EmailSender
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

interface IAuthService {
    fun initiatePasswordReset(email: String)
    fun resetPassword(token: String, newPassword: String)
}

@Service
class AuthService(
    private val passwordResetTokenRepository: PasswordResetTokenRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val emailSender: EmailSender,
    private val eventPublisher: ApplicationEventPublisher
) : IAuthService {
    override fun initiatePasswordReset(email: String) {
        val user = userRepository.findByEmail(email)
            ?: throw UserNotFoundException("User with email $email not found")
        if (!user.isEmailVerified) {
            throw EmailNotVerifiedException("Email not verified for $email")
        }
        if (user.isDisabled) {
            throw UserDisabledException("User account is disabled")
        }

        val passwordResetToken = PasswordResetTokenFactory.createToken(user)
        passwordResetTokenRepository.save(passwordResetToken)

        val resetLink = "https://yourdomain.com/reset-password?token=${passwordResetToken.token}"
        eventPublisher.publishEvent(PasswordResetRequestedEvent(user, resetLink))
    }

    override fun resetPassword(token: String, newPassword: String) {
        val tokenEntity = passwordResetTokenRepository.findByToken(token)
            ?: throw TokenNotFoundException("Token not found")
        if (tokenEntity.isUsed) {
            throw TokenAlreadyUsedException("Token has already been used")
        }
        if (tokenEntity.expiryDate.isBefore(LocalDateTime.now())) {
            throw TokenExpiredException("Token expired")
        }
        if (newPassword.length < 8) {
            throw PasswordTooWeakException("Password must be at least 8 characters long")
        }
        val user = tokenEntity.user
        if (user.isLocked) {
            throw AccountLockedException("Account is locked")
        }
        user.passwordHash = passwordEncoder.encode(newPassword)
        userRepository.save(user)
        passwordResetTokenRepository.delete(tokenEntity)
    }
}