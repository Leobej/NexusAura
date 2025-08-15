package com.nexus.aura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nexus.aura.ui.feature.navbar.MainNavScaffold
import com.nexus.aura.ui.theme.NexusAuraAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NexusAuraAndroidTheme {
                MainNavScaffold()
            }
        }
    }
}
