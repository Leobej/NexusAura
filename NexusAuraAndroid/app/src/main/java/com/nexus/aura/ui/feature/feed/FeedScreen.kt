package com.nexus.aura.ui.feature.feed

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexus.aura.data.model.Post
import com.nexus.aura.ui.feature.post.PostCard

@Composable
fun FeedScreen(
    posts: List<Post>,
    onLikeClicked: (Post) -> Unit,
    onCommentClicked: (Post) -> Unit,
    onProfileClicked: (String) -> Unit,
    modifier: Modifier
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(posts) { post ->
            PostCard(
                post = post,
                onLikeClicked = { onLikeClicked(post) },
                onCommentClicked = { onCommentClicked(post) },
                onProfileClicked = { onProfileClicked(post.userId) }
            )
        }
    }
}