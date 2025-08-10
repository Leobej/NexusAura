package com.nexus.aura.ui.feature.navbar


import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nexus.aura.domain.models.FeedViewModel
import com.nexus.aura.ui.feature.activity.ActivityScreen
import com.nexus.aura.ui.feature.create.CreateScreen
import com.nexus.aura.ui.feature.feed.FeedScreen
import com.nexus.aura.ui.feature.profile.ProfileScreen
import com.nexus.aura.ui.feature.search.SearchScreen

enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    FEED("feed", "Feed", Icons.Default.MusicNote, "Feed"),
    SEARCH("search", "Search", Icons.Default.Search, "Search"),
    CREATE("create", "Create", Icons.Default.AddCircle, "Create"),
    ACTIVITY("activity", "Activity", Icons.Default.Notifications, "Activity"),
    PROFILE("profile", "Profile", Icons.Default.Person, "Profile")
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = startDestination.route,
        modifier = modifier
    ) {
        composable(Destination.FEED.route) {
            val feedViewModel: FeedViewModel = hiltViewModel()
            val posts by feedViewModel.feedPosts.collectAsState()
            FeedScreen(
                posts = posts,
                onLikeClicked = { feedViewModel.toggleLike(it) },
                onCommentClicked = { /* TODO */ },
                onProfileClicked = { /* TODO */ },
                modifier = Modifier
            )
        }
        composable(Destination.SEARCH.route) { SearchScreen() }
        composable(Destination.CREATE.route) { CreateScreen() }
        composable(Destination.ACTIVITY.route) { ActivityScreen() }
        composable(Destination.PROFILE.route) { ProfileScreen() }
    }
}

@Preview()
@Composable
fun NavigationBarExample(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val startDestination = Destination.FEED
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                Destination.entries.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = selectedDestination == index,
                        onClick = {
                            navController.navigate(route = destination.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                            selectedDestination = index
                        },
                        icon = {
                            Icon(
                                destination.icon,
                                contentDescription = destination.contentDescription
                            )
                        },
                        label = { Text(destination.label) }
                    )
                }
            }
        }
    ) { contentPadding ->
        AppNavHost(navController, startDestination, modifier = Modifier.padding(contentPadding))
    }
}
