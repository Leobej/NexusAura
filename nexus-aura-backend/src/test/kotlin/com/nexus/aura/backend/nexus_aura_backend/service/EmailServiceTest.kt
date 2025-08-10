package com.nexus.aura.backend.nexus_aura_backend.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender

@SpringBootTest
class EmailServiceTest {

    @Autowired
    private lateinit var emailService: EmailService

    @MockBean
    private lateinit var mailSender: JavaMailSender

    @Test
    fun contextLoads() {
        assertNotNull(emailService)
    }

    @Test
    fun sendPasswordResetEmail() {
        val email = "test@example.com"
        val resetLink = "http://example.com/reset?token=123"

        emailService.sendPasswordResetEmail(email, resetLink)

        val messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage::class.java)
        verify(mailSender).send(messageCaptor.capture())
        val message = messageCaptor.value

        assertEquals(email, message.to?.get(0))
        assertEquals("Password Reset Request", message.subject)
        assertEquals("To reset your password, click the link below:\n$resetLink", message.text)
    }
}