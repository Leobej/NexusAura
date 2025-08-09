package com.nexus.aura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.nexus.aura.domain.models.FeedViewModel
import com.nexus.aura.ui.feature.feed.FeedScreen
import com.nexus.aura.ui.theme.NexusAuraAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NexusAuraAndroidTheme {
                val feedViewModel: FeedViewModel = hiltViewModel()
                val posts by feedViewModel.feedPosts.collectAsState()

                Scaffold { innerPadding ->
                    FeedScreen(
                        posts = posts,
                        onLikeClicked = { feedViewModel.toggleLike(it) },
                        onCommentClicked = { /* navigate to comment */ },
                        onProfileClicked = { /* TODO */ },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NexusAuraAndroidTheme {
        Greeting("Android")
    }
}