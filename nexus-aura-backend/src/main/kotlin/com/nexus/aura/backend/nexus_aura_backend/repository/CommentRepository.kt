package com.nexus.aura.backend.nexus_aura_backend.repository

import com.nexus.aura.backend.nexus_aura_backend.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CommentRepository : JpaRepository<Comment, UUID> {
    fun findAllByPostId(postId: UUID): List<Comment>
}

