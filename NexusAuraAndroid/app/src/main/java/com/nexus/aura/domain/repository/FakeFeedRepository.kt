package com.nexus.aura.domain.repository

import com.nexus.aura.data.model.Post
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeFeedRepository @Inject constructor() : FeedRepository {
    override suspend fun getFeedPosts(): List<Post> {
        // Simulate network delay
        delay(1000)
        return listOf(
            Post(
                id = "1",
                userId = "u1",
                username = "alex_photos",
                userProfilePic = "https://randomuser.me/api/portraits/men/1.jpg",
                imageUrl = "https://picsum.photos/600/300",
                caption = "Golden hour magic 🌇",
                timestamp = System.currentTimeMillis()
            )
        )
    }
}