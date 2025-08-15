package com.nexus.aura.ui.feature.post

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexus.aura.data.model.Post
import com.nexus.aura.ui.feature.story.StoryBar
import com.nexus.aura.ui.feature.story.mockStories

@Composable
fun PostFeed(
    posts: List<Post>,
    onLikeClicked: (Post) -> Unit = {},
    onCommentClicked: (Post) -> Unit = {},
    onProfileClicked: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        StoryBar(stories = mockStories)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize()
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
}
