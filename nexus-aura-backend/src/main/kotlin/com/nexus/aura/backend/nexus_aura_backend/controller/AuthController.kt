package com.nexus.aura.backend.nexus_aura_backend.controller

import com.nexus.aura.backend.nexus_aura_backend.dto.ResetPasswordRequest
import com.nexus.aura.backend.nexus_aura_backend.dto.UserRegistrationRequest
import com.nexus.aura.backend.nexus_aura_backend.dto.UserResponse
import com.nexus.aura.backend.nexus_aura_backend.entity.EmailRequest
import com.nexus.aura.backend.nexus_aura_backend.entity.JwtResponse
import com.nexus.aura.backend.nexus_aura_backend.entity.LoginRequest
import com.nexus.aura.backend.nexus_aura_backend.exception.InvalidTokenException
import com.nexus.aura.backend.nexus_aura_backend.exception.TokenExpiredException
import com.nexus.aura.backend.nexus_aura_backend.service.AuthService
import com.nexus.aura.backend.nexus_aura_backend.service.UserService
import com.nexus.aura.backend.nexus_aura_backend.util.JwtUtil
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*
import javax.naming.AuthenticationException

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    private val authService: AuthService,
    private val jwtUtil: JwtUtil
) {
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerUser(@Valid @RequestBody request: UserRegistrationRequest): UserResponse {
        return userService.registerUser(request)
    }

    @PostMapping("/login")
    fun authenticateUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<*> {
        val user = userService.findUserByIdentifier(loginRequest.identifier)
            ?: return ResponseEntity.status(404).body("User not found")

        return try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(user.email, loginRequest.password)
            )

            val userDetails = authentication.principal as User
            val jwt = jwtUtil.generateToken(userDetails.username)

            ResponseEntity.ok(JwtResponse(jwt))
        } catch (e: AuthenticationException) {
            ResponseEntity.status(401).body("Invalid credentials")
        }
    }

    @PostMapping("/request-reset")
    fun requestPasswordReset(@RequestBody request: EmailRequest): ResponseEntity<String> {
        return try {
            authService.initiatePasswordReset(request.email)
            ResponseEntity.ok("Password reset email sent")
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
        }
    }

    @PostMapping("/reset-password")
    fun resetPassword(@RequestBody request: ResetPasswordRequest): ResponseEntity<String> {
        return try {
            authService.resetPassword(request.token, request.newPassword)
            ResponseEntity.ok("Password successfully reset")
        } catch (e: InvalidTokenException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token")
        } catch (e: TokenExpiredException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token expired")
        }
    }

}