package com.nexus.aura.backend.nexus_aura_backend.controller

import com.nexus.aura.backend.nexus_aura_backend.dto.UserProfileUpdateRequest
import com.nexus.aura.backend.nexus_aura_backend.dto.UserResponse
import com.nexus.aura.backend.nexus_aura_backend.service.IUserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: IUserService
) {

    @PutMapping("/profile")
    fun updateProfile(
        authentication: Authentication,
        @Valid @RequestBody request: UserProfileUpdateRequest
    ): ResponseEntity<*> {
        return try {
            val email = authentication.name
            val updatedUser = userService.updateUserProfile(email, request)
            ResponseEntity.ok(updatedUser)
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.UserNotFoundException) {
            ResponseEntity.status(404).body(e.message ?: "User not found")
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.UserDisabledException) {
            ResponseEntity.status(403).body(e.message ?: "User account is disabled")
        } catch (e: Exception) {
            ResponseEntity.status(500).body("An unexpected error occurred")
        }
    }
}