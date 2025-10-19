package com.example.arambh.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.arambh.R
import com.example.arambh.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val neonGreen = Color(0xFF00FF88)
    val darkBg = Color(0xFF0A0A0A)

    // Navigate to Login after 2 seconds
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(Screen.LogInScreen.route) {
            popUpTo(0)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBg),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.whatsapp_image_2025_09_15_at_00_28_41_82303890),
                contentDescription = "App Logo",
                modifier = Modifier.size(180.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Aarambh",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = neonGreen
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Ignite Your Potential",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    }
}
