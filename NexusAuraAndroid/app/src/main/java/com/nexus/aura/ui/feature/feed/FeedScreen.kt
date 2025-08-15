package com.nexus.aura.ui.feature.feed

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexus.aura.data.model.Post
import com.nexus.aura.data.model.mockPosts
import com.nexus.aura.ui.feature.post.PostFeed

@Composable
fun FeedScreen(
    posts: List<Post> = mockPosts,
    onLikeClicked: (Post) -> Unit = {},
    onCommentClicked: (Post) -> Unit = {},
    onProfileClicked: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "NexusAura",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { /* TODO: Notifications action */ }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications"
                )
            }
            IconButton(onClick = { /* TODO: Message action */ }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Messages"
                )
            }
        }
        PostFeed(
            posts = posts,
            onLikeClicked = onLikeClicked,
            onCommentClicked = onCommentClicked,
            onProfileClicked = onProfileClicked,
            modifier = Modifier.weight(1f)
        )
    }
}