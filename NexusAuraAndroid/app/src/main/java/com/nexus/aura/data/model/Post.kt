package com.nexus.aura.data.model

data class Post(
    val id: String,
    val userId: String,
    val username: String,
    val userProfilePic: String,
    val imageUrl: String,
    val caption: String,
    val timestamp: Long,
    val isLiked: Boolean = false
)

val mockPosts = listOf(
    Post(
        id = "1",
        userId = "u1",
        username = "Alice",
        userProfilePic = "",
        imageUrl = "",
        caption = "Enjoying the summer vibes! ☀️",
        timestamp = 1629118800000,
        isLiked = false
    ),
    Post(
        id = "2",
        userId = "u2",
        username = "Bob",
        userProfilePic = "",
        imageUrl = "",
        caption = "Just finished a great book! 📚",
        timestamp = 1629122400000,
        isLiked = true
    ),
    Post(
        id = "3",
        userId = "u3",
        username = "Carol",
        userProfilePic = "",
        imageUrl = "",
        caption = "Hiking adventures in the mountains! 🏔️",
        timestamp = 1629126000000,
        isLiked = false
    ),
    Post(
        id = "4",
        userId = "u4",
        username = "Dave",
        userProfilePic = "",
        imageUrl = "",
        caption = "Delicious homemade pizza tonight! 🍕",
        timestamp = 1629129600000,
        isLiked = false
    ),
    Post(
        id = "5",
        userId = "u5",
        username = "Eve",
        userProfilePic = "",
        imageUrl = "",
        caption = "Sunset by the beach. 🌅",
        timestamp = 1629133200000,
        isLiked = true
    ),
    Post(
        id = "6",
        userId = "u6",
        username = "Frank",
        userProfilePic = "",
        imageUrl = "",
        caption = "Coding late into the night! 💻",
        timestamp = 1629136800000,
        isLiked = false
    ),
    Post(
        id = "7",
        userId = "u7",
        username = "Grace",
        userProfilePic = "",
        imageUrl = "",
        caption = "Coffee break with friends ☕",
        timestamp = 1629140400000,
        isLiked = false
    ),
    Post(
        id = "8",
        userId = "u8",
        username = "Heidi",
        userProfilePic = "",
        imageUrl = "",
        caption = "Trying out a new recipe! 🍰",
        timestamp = 1629144000000,
        isLiked = true
    ),
    Post(
        id = "9",
        userId = "u9",
        username = "Ivan",
        userProfilePic = "",
        imageUrl = "",
        caption = "Morning run complete! 🏃‍♂️",
        timestamp = 1629147600000,
        isLiked = false
    ),
    Post(
        id = "10",
        userId = "u10",
        username = "Judy",
        userProfilePic = "",
        imageUrl = "",
        caption = "Movie night with the squad! 🎬",
        timestamp = 1629151200000,
        isLiked = false
    )
)
