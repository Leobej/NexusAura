package com.nexus.aura.backend.nexus_aura_backend.service

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class EmailServiceTest {

    @Autowired
    private lateinit var emailService: EmailService

    @Test
    fun contextLoads() {
        assertNotNull(emailService)
    }

    @Test
    fun sendPasswordResetEmail() {
        // Implement your test logic here
    }
}