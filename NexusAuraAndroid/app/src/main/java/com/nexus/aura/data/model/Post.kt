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