package com.nexus.aura.backend.nexus_aura_backend.controller

import com.nexus.aura.backend.nexus_aura_backend.dto.CreatePostRequest
import com.nexus.aura.backend.nexus_aura_backend.dto.UserProfileUpdateRequest
import com.nexus.aura.backend.nexus_aura_backend.dto.UserResponse
import com.nexus.aura.backend.nexus_aura_backend.service.IUserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

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
            val username = authentication.name
            val updatedUser = userService.updateUserProfile(username, request)
            ResponseEntity.ok(updatedUser)
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.UserNotFoundException) {
            ResponseEntity.status(404).body(e.message ?: "User not found")
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.UserDisabledException) {
            ResponseEntity.status(403).body(e.message ?: "User account is disabled")
        } catch (e: Exception) {
            ResponseEntity.status(500).body("An unexpected error occurred")
        }
    }

    @GetMapping("/profile")
    fun getProfile(authentication: Authentication): ResponseEntity<*> {
        return try{
            val username = authentication.name
            val user = userService.getUserByUsername(username)
            ResponseEntity.ok(user)
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.UserNotFoundException) {
            ResponseEntity.status(404).body(e.message ?: "User not found")
        } catch (e: Exception) {
            ResponseEntity.status(500).body("An unexpected error occurred")
        }
    }

    @GetMapping("/posts")
    fun getUserPosts(authentication: Authentication): ResponseEntity<*> {
        return try{
            val username = authentication.name
            val posts = userService.getUserPosts(username)
            ResponseEntity.ok(posts)
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.UserNotFoundException) {
            ResponseEntity.status(404).body(e.message ?: "User not found")
        } catch (e: Exception) {
            ResponseEntity.status(500).body("An unexpected error occurred")
        }

    }

    @PostMapping("/posts")
    fun createPost(authentication: Authentication, @Valid @RequestBody request: CreatePostRequest): ResponseEntity<*> {
        return try {
            val username = authentication.name
            val post = userService.createPost(username, request)
            ResponseEntity.ok(post)
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.UserNotFoundException) {
            ResponseEntity.status(404).body(e.message ?: "User not found")
        } catch (e: Exception) {
            ResponseEntity.status(500).body("An unexpected error occurred")
        }
    }

    @GetMapping("/feed")
    fun getUserFeed(authentication: Authentication): ResponseEntity<*> {
        return try {
            val username = authentication.name
            val feed = userService.getUserFeed(username)
            ResponseEntity.ok(feed)
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.UserNotFoundException) {
            ResponseEntity.status(404).body(e.message ?: "User not found")
        } catch (e: Exception) {
            ResponseEntity.status(500).body("An unexpected error occurred")
        }
    }

    @PostMapping("/posts/{postId}/comments")
    fun addCommentToPost(
        authentication: Authentication,
        @PathVariable postId: java.util.UUID,
        @RequestBody body: Map<String, String>
    ): ResponseEntity<*> {
        return try {
            val username = authentication.name
            val content = body["content"] ?: return ResponseEntity.badRequest().body("Missing comment content")
            val comment = userService.addCommentToPost(username, postId, content)
            ResponseEntity.ok(comment)
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.UserNotFoundException) {
            ResponseEntity.status(404).body(e.message ?: "User or Post not found")
        } catch (e: Exception) {
            ResponseEntity.status(500).body("An unexpected error occurred")
        }
    }

    @PostMapping("/posts/{postId}/like")
    fun likePost(
        @PathVariable postId: java.util.UUID
    ): ResponseEntity<*> {
        return try {
            val post = userService.likePost(postId)
            ResponseEntity.ok(post)
        } catch (e: com.nexus.aura.backend.nexus_aura_backend.exception.UserNotFoundException) {
            ResponseEntity.status(404).body(e.message ?: "Post not found")
        } catch (e: Exception) {
            ResponseEntity.status(500).body("An unexpected error occurred")
        }
    }
}