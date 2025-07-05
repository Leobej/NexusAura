package com.nexus.aura.backend.nexus_aura_backend.service.email

interface EmailSender {
    fun send(to: String, subject: String, body: String)
}

