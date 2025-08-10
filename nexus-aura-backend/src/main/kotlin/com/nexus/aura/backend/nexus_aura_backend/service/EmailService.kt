package com.nexus.aura.backend.nexus_aura_backend.service

import com.nexus.aura.backend.nexus_aura_backend.service.email.EmailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(private val mailSender: JavaMailSender) : EmailSender {
    override fun send(to: String, subject: String, body: String) {
        val message = SimpleMailMessage().apply {
            setTo(to)
            this.subject = subject
            this.text = body
        }
        mailSender.send(message)
    }

    fun sendPasswordResetEmail(to: String, resetLink: String) {
        send(to, "Password Reset Request", "To reset your password, click the link below:\n$resetLink")
    }
}