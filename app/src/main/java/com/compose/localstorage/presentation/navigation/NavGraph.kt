package com.compose.localstorage.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.compose.localstorage.presentation.auth.AuthManager
import com.compose.localstorage.presentation.auth.LoginScreen
import com.compose.localstorage.presentation.dashboard.DashboardScreen

import com.compose.localstorage.presentation.splash.SplashScreen
import com.compose.localstorage.presentation.navigation.Screen

@Composable
fun NavGraph(
    authManager: AuthManager
) {
    val navController = rememberNavController()
    val isLoggedIn by authManager.isLoggedIn.collectAsState()
    val isRestoring by authManager.isRestoring.collectAsState()

    LaunchedEffect(isLoggedIn, isRestoring) {
        if (!isRestoring) {
            val currentRoute = navController.currentBackStackEntry?.destination?.route
            if (isLoggedIn && currentRoute != Screen.Dashboard.route) {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                    launchSingleTop = true
                }
            } else if (!isLoggedIn && currentRoute != Screen.Auth.route) {
                navController.navigate(Screen.Auth.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(authManager = authManager)
        }
        composable(Screen.Auth.route) {
            LoginScreen()
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(authManager = authManager)
        }
    }
}
