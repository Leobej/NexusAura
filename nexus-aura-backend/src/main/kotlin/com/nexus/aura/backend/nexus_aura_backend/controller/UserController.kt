package com.nexus.aura.backend.nexus_aura_backend.controller

import com.nexus.aura.backend.nexus_aura_backend.dto.UserProfileUpdateRequest
import com.nexus.aura.backend.nexus_aura_backend.dto.UserResponse
import com.nexus.aura.backend.nexus_aura_backend.service.UserService
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
    private val userService: UserService
) {

    @PutMapping("/profile")
    fun updateProfile(
        authentication: Authentication,
        @Valid @RequestBody request: UserProfileUpdateRequest
    ): ResponseEntity<UserResponse> {
        val username = authentication.name
        val updatedUser = userService.updateUserProfile(username, request)
        return ResponseEntity.ok(updatedUser)
    }
}