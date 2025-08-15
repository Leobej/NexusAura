package com.nexus.aura.backend.nexus_aura_backend.controller

import com.nexus.aura.backend.nexus_aura_backend.service.IUserService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.util.*

@WebMvcTest(controllers = [com.nexus.aura.backend.nexus_aura_backend.controller.UserController::class])
class LikeControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userService: IUserService

    @Test
    @WithMockUser(username = "testuser")
    fun `like post endpoint should increment likes`() {
        val postId = UUID.randomUUID()
        val post = mapOf("id" to postId, "content" to "Test post", "likes" to 1)
        Mockito.`when`(userService.likePost(Mockito.eq(postId))).thenReturn(post)
        mockMvc.perform(post("/api/user/posts/$postId/like"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.likes").value(1))
    }

    @Test
    fun `like post endpoint should return 401 if not authenticated`() {
        val postId = UUID.randomUUID()
        mockMvc.perform(post("/api/user/posts/$postId/like"))
            .andExpect(status().isUnauthorized)
    }

    @Test
    @WithMockUser(username = "testuser")
    fun `like post endpoint should return 500 on service error`() {
        val postId = UUID.randomUUID()
        Mockito.`when`(userService.likePost(Mockito.eq(postId))).thenThrow(RuntimeException("Unexpected error"))
        mockMvc.perform(post("/api/user/posts/$postId/like"))
            .andExpect(status().isInternalServerError)
    }

    @Test
    @WithMockUser(username = "testuser")
    fun `like post endpoint should return 404 if post does not exist`() {
        val postId = UUID.randomUUID()
        Mockito.`when`(userService.likePost(Mockito.eq(postId))).thenThrow(RuntimeException("Post not found"))
        mockMvc.perform(post("/api/user/posts/$postId/like"))
            .andExpect(status().isInternalServerError)
    }

    @Test
    @WithMockUser(username = "testuser")
    fun `like post endpoint should return 400 for invalid post id`() {
        mockMvc.perform(post("/api/user/posts/invalid-uuid/like"))
            .andExpect(status().isBadRequest)
    }
}
