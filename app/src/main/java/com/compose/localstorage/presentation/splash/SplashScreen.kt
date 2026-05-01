package com.compose.localstorage.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.localstorage.presentation.auth.AuthManager
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    authManager: AuthManager
) {
    LaunchedEffect(Unit) {
        delay(2000) // Delay for splash effect
        authManager.restoreSession()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Coderkube",
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF1B3B5A),
                letterSpacing = 0.5.sp
            )
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Task",
                    fontSize = 52.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF3B76D1)
                )
                Text(
                    text = "Planner",
                    fontSize = 52.sp,
                    fontWeight = FontWeight.Light,
                    color = Color(0xFF1B3B5A)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF3B76D1),
                    modifier = Modifier
                        .size(32.dp)
                        .offset(y = (-4).dp)
                )
            }
        }
    }
}
