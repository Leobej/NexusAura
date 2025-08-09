package com.nexus.aura.domain.repository

import com.nexus.aura.data.model.Post

interface FeedRepository {
    suspend fun getFeedPosts(): List<Post>
}
