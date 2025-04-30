package com.nexus.aura.backend.nexus_aura_backend.controller
import com.nexus.aura.backend.nexus_aura_backend.dto.UserRegistrationRequest
import com.nexus.aura.backend.nexus_aura_backend.dto.UserResponse
import com.nexus.aura.backend.nexus_aura_backend.entity.JwtResponse
import com.nexus.aura.backend.nexus_aura_backend.entity.LoginRequest
import com.nexus.aura.backend.nexus_aura_backend.service.UserService
import com.nexus.aura.backend.nexus_aura_backend.util.JwtUtil
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*
import javax.naming.AuthenticationException

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil
)  {
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerUser(@Valid @RequestBody request: UserRegistrationRequest): UserResponse {
        return userService.registerUser(request)
    }

    @PostMapping("/login")
    fun authenticateUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<*> {
        return try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
            )

            val userDetails = authentication.principal as org.springframework.security.core.userdetails.User
            val jwt = jwtUtil.generateToken(userDetails.username)

            ResponseEntity.ok(JwtResponse(jwt))
        } catch (e: AuthenticationException) {
            ResponseEntity.status(401).body("Invalid email or password")
        }
    }
}