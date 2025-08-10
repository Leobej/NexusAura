package com.nexus.aura.routes

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nexus.aura.domain.models.FeedViewModel
import com.nexus.aura.ui.feature.feed.FeedScreen

@Composable
fun FeedRoute() {
    val viewModel: FeedViewModel = hiltViewModel()
    val posts by viewModel.feedPosts.collectAsState()

    FeedScreen(
        posts = posts,
        onLikeClicked = { viewModel.toggleLike(it) },
        onCommentClicked = { /* TODO */ },
        onProfileClicked = { /* TODO */ },
        modifier = Modifier.Companion.padding(16.dp)
    )
}