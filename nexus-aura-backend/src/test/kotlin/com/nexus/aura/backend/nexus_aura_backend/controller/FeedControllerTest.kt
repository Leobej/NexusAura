package com.nexus.aura.backend.nexus_aura_backend.controller

import com.nexus.aura.backend.nexus_aura_backend.service.IUserService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@WebMvcTest(controllers = [com.nexus.aura.backend.nexus_aura_backend.controller.UserController::class])
class FeedControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userService: IUserService

    @Test
    @WithMockUser(username = "testuser")
    fun `feed endpoint should return feed`() {
        val feed = listOf(mapOf("id" to UUID.randomUUID(), "content" to "Feed post"))
        Mockito.`when`(userService.getUserFeed(Mockito.anyString())).thenReturn(feed)
        mockMvc.perform(get("/api/user/feed"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].content").value("Feed post"))
    }

    @Test
    @WithMockUser(username = "testuser")
    fun `feed endpoint should return 500 on service error`() {
        Mockito.`when`(userService.getUserFeed(Mockito.anyString())).thenThrow(RuntimeException("Feed error"))
        mockMvc.perform(get("/api/user/feed"))
            .andExpect(status().isInternalServerError)
    }

    @Test
    fun `feed endpoint should return 401 if not authenticated`() {
        mockMvc.perform(get("/api/user/feed"))
            .andExpect(status().isUnauthorized)
    }
}
