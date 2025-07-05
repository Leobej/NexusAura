package com.nexus.aura.backend.nexus_aura_backend.event

import com.nexus.aura.backend.nexus_aura_backend.service.email.EmailSender
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UserEventListener(private val emailSender: EmailSender) {
    @EventListener
    fun handleUserRegistered(event: UserRegisteredEvent) {
        val user = event.user
        if (user.email.isNotBlank()) {
            emailSender.send(
                user.email,
                "Welcome to Nexus Aura!",
                "Hello ${user.username},\n\nThank you for registering at Nexus Aura! Enjoy connecting with the community."
            )
        }
    }

    @EventListener
    fun handlePasswordResetRequested(event: PasswordResetRequestedEvent) {
        val user = event.user
        emailSender.send(
            user.email,
            "Password Reset Request",
            "To reset your password, click the link below:\n${event.resetLink}"
        )
    }
}

