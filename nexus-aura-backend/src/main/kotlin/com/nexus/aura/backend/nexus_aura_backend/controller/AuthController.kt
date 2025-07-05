package com.nexus.aura.backend.nexus_aura_backend.controller

import com.nexus.aura.backend.nexus_aura_backend.dto.*
import com.nexus.aura.backend.nexus_aura_backend.exception.InvalidTokenException
import com.nexus.aura.backend.nexus_aura_backend.exception.TokenExpiredException
import com.nexus.aura.backend.nexus_aura_backend.service.IAuthService
import com.nexus.aura.backend.nexus_aura_backend.service.IJwtBlacklistService
import com.nexus.aura.backend.nexus_aura_backend.service.IUserService
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
    private val userService: IUserService,
    private val authenticationManager: AuthenticationManager,
    private val authService: IAuthService,
    private val jwtUtil: JwtUtil,
    private val jwtBlacklistService: IJwtBlacklistService
) {
    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody request: UserRegistrationRequest): ResponseEntity<*> {
        return try {
            val userResponse = userService.registerUser(request)
            ResponseEntity.status(HttpStatus.CREATED).body(userResponse)
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.UserAlreadyExistsException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(mapOf("error" to (e.message ?: "Email is already in use")))
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.UsernameAlreadyTakenException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(mapOf("error" to (e.message ?: "Username is already taken")))
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.PasswordTooWeakException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to (e.message ?: "Password is too weak")))
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf("error" to (e.message ?: "An unexpected error occurred")))
        }
    }

    @PostMapping("/login")
    fun authenticateUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<*> {
        val user = userService.findUserByIdentifier(loginRequest.identifier)
            ?: return ResponseEntity.status(404).body(mapOf("error" to "User not found"))

        return try {
            if (user.isLocked) {
                throw com.nexus.aura.backend.nexus_aura_backend.exception.AccountLockedException("Account is locked")
            }
//            if (!user.isEmailVerified) {
//                throw com.nexus.aura.backend.nexus_aura_backend.exception.EmailNotVerifiedException("Email not verified")
//            }
            if (user.isDisabled) {
                throw com.nexus.aura.backend.nexus_aura_backend.exception.UserDisabledException("User account is disabled")
            }
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(user.email, loginRequest.password)
            )
            val userDetails = authentication.principal as User
            val jwt = jwtUtil.generateToken(userDetails.username)
            ResponseEntity.ok(JwtResponse(jwt))
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.AccountLockedException) {
            ResponseEntity.status(423).body(mapOf("error" to (e.message ?: "Account is locked")))
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.EmailNotVerifiedException) {
            ResponseEntity.status(403).body(mapOf("error" to (e.message ?: "Email not verified")))
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.UserDisabledException) {
            ResponseEntity.status(403).body(mapOf("error" to (e.message ?: "User account is disabled")))
        } catch (e: AuthenticationException) {
            ResponseEntity.status(401).body(mapOf("error" to "Invalid credentials"))
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf("error" to "An unexpected error occurred"))
        }
    }

    @PostMapping("/request-reset")
    fun requestPasswordReset(@RequestBody request: EmailRequest): ResponseEntity<String> {
        return try {
            authService.initiatePasswordReset(request.email)
            ResponseEntity.ok("Password reset email sent")
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.UserNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message ?: "User not found")
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.EmailNotVerifiedException) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.message ?: "Email not verified")
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.UserDisabledException) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.message ?: "User account is disabled")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred")
        }
    }

    @PostMapping("/reset-password")
    fun resetPassword(@RequestBody request: ResetPasswordRequest): ResponseEntity<String> {
        return try {
            authService.resetPassword(request.token, request.newPassword)
            ResponseEntity.ok("Password successfully reset")
        } catch (e: InvalidTokenException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message ?: "Invalid token")
        } catch (e: TokenExpiredException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message ?: "Token expired")
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.TokenNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message ?: "Token not found")
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.TokenAlreadyUsedException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message ?: "Token already used")
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.PasswordTooWeakException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message ?: "Password is too weak")
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.AccountLockedException) {
            ResponseEntity.status(423).body(e.message ?: "Account is locked")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred")
        }
    }

    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") authHeader: String?): ResponseEntity<*> {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid Authorization header")
        }

        val token = authHeader.removePrefix("Bearer ").trim()

        jwtBlacklistService.blacklistToken(token)

        return ResponseEntity.ok(LogoutResponse("Successfully logged out"))
    }
}