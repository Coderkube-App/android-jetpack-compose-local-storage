package com.compose.localstorage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import com.compose.localstorage.presentation.auth.AuthManager
import com.compose.localstorage.presentation.navigation.NavGraph
import com.compose.localstorage.ui.theme.ComposeLocalStorageTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLocalStorageTheme {
                NavGraph(authManager = authManager)
            }
        }
    }
}