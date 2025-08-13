package com.nexus.aura.backend.nexus_aura_backend.repository

import com.nexus.aura.backend.nexus_aura_backend.entity.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PostRepository : JpaRepository<Post, UUID> {
    fun findAllByUserId(userId: UUID): List<Post>
    fun findAllByUserIdInOrderByCreatedAtDesc(userIds: List<UUID>): List<Post>
}
