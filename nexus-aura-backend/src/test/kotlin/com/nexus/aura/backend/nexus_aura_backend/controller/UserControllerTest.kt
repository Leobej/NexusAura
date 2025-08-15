package com.nexus.aura.backend.nexus_aura_backend.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.nexus.aura.backend.nexus_aura_backend.dto.CreatePostRequest
import com.nexus.aura.backend.nexus_aura_backend.service.IUserService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@WebMvcTest(UserController::class)
class UserControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userService: IUserService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    @WithMockUser(username = "testuser")
    fun `create post endpoint should return post`() {
        val request = CreatePostRequest(content = "Hello World!")
        val postId = UUID.randomUUID()
        val post = mapOf("id" to postId, "content" to request.content)
        Mockito.`when`(userService.createPost(Mockito.anyString(), Mockito.any())).thenReturn(post)
        mockMvc.perform(post("/api/user/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk)
    }

    @Test
    @WithMockUser(username = "testuser")
    fun `add comment endpoint should return comment`() {
        val postId = UUID.randomUUID()
        val comment = mapOf("id" to UUID.randomUUID(), "content" to "Nice post!")
        Mockito.`when`(userService.addCommentToPost(Mockito.anyString(), Mockito.eq(postId), Mockito.anyString())).thenReturn(comment)
        mockMvc.perform(post("/api/user/posts/$postId/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{""content"":""Nice post!""}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content").value("Nice post!"))
    }

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
    @WithMockUser(username = "testuser")
    fun `create post endpoint should return 400 for missing content`() {
        mockMvc.perform(post("/api/user/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
            .andExpect(status().isBadRequest)
    }

    @Test
    @WithMockUser(username = "testuser")
    fun `add comment endpoint should return 400 for missing content`() {
        val postId = UUID.randomUUID()
        mockMvc.perform(post("/api/user/posts/$postId/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
            .andExpect(status().isBadRequest)
    }

    @Test
    @WithMockUser(username = "testuser")
    fun `like post endpoint should return 404 for not found`() {
        val postId = UUID.randomUUID()
        Mockito.`when`(userService.likePost(Mockito.eq(postId))).thenThrow(RuntimeException("Post not found"))
        mockMvc.perform(post("/api/user/posts/$postId/like"))
            .andExpect(status().isInternalServerError)
    }

    @Test
    @WithMockUser(username = "testuser")
    fun `add comment endpoint should return 404 for not found`() {
        val postId = UUID.randomUUID()
        Mockito.`when`(userService.addCommentToPost(Mockito.anyString(), Mockito.eq(postId), Mockito.anyString())).thenThrow(RuntimeException("Post not found"))
        mockMvc.perform(post("/api/user/posts/$postId/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{""content"":""Nice post!""}"))
            .andExpect(status().isInternalServerError)
    }

    // Add more endpoint tests as needed
}
