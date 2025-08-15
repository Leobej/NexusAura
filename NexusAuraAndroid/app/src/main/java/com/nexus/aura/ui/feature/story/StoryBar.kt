package com.nexus.aura.ui.feature.story

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nexus.aura.R

// Mock Story data model
 data class Story(
    val id: String,
    val username: String,
    val avatarRes: Int, // Resource ID for avatar image
    val hasStory: Boolean = true
)

// Mock data
val mockStories = listOf(
    Story("1", "You", R.drawable.ic_launcher_foreground, true),
    Story("2", "Alice", R.drawable.ic_launcher_foreground, true),
    Story("3", "Bob", R.drawable.ic_launcher_foreground, false),
    Story("4", "Carol", R.drawable.ic_launcher_foreground, true),
    Story("5", "Dave", R.drawable.ic_launcher_foreground, true),
    Story("6", "Eve", R.drawable.ic_launcher_foreground, false)
)

@Composable
fun StoryBar(stories: List<Story>, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(stories) { story ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = story.avatarRes),
                    contentDescription = story.username,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = story.username,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
