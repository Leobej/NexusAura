package com.nexus.aura.backend.nexus_aura_backend.event

import com.nexus.aura.backend.nexus_aura_backend.entity.User
import org.springframework.context.ApplicationEvent

class UserRegisteredEvent(val user: User) : ApplicationEvent(user)
class PasswordResetRequestedEvent(val user: User, val resetLink: String) : ApplicationEvent(user)

