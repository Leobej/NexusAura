package com.nexus.aura.backend.nexus_aura_backend.controller

import com.nexus.aura.backend.nexus_aura_backend.service.IUserService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.util.*

@WebMvcTest(controllers = [com.nexus.aura.backend.nexus_aura_backend.controller.UserController::class])
class CommentControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userService: IUserService

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
    fun `add comment endpoint should return 401 if not authenticated`() {
        val postId = UUID.randomUUID()
        mockMvc.perform(post("/api/user/posts/$postId/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{""content"":""Nice post!""}"))
            .andExpect(status().isUnauthorized)
    }

    @Test
    @WithMockUser(username = "testuser")
    fun `add comment endpoint should return 500 on service error`() {
        val postId = UUID.randomUUID()
        Mockito.`when`(userService.addCommentToPost(Mockito.anyString(), Mockito.eq(postId), Mockito.anyString())).thenThrow(RuntimeException("Unexpected error"))
        mockMvc.perform(post("/api/user/posts/$postId/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{""content"":""Nice post!""}"))
            .andExpect(status().isInternalServerError)
    }
}
