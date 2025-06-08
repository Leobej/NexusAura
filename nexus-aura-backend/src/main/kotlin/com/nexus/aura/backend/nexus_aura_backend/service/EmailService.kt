package com.nexus.aura.backend.nexus_aura_backend.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(private val mailSender: JavaMailSender) {

    fun sendPasswordResetEmail(to: String, resetLink: String) {
        val message = SimpleMailMessage()
        message.setTo(to)
        message.subject = "Password Reset Request"
        message.text = "To reset your password, click the link below:\n$resetLink"
        mailSender.send(message)
    }
}