package com.example.arambh.ActivityScreens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.arambh.R
import com.example.arambh.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SquatScreen(navController: NavController) {
    val MatteBlack = Color(0xFF000000)
    val NeonGreen = Color(0xFF00FF66)
    val NeonPink = Color(0xFFFF00FF)

    Scaffold(
        containerColor = MatteBlack,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Squat",
                        style = MaterialTheme.typography.titleLarge,
                        color = NeonGreen
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Screen.HomeScreen.route)}) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = NeonGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MatteBlack
                )
            )
        },
        bottomBar = {
            NavigationBar(containerColor = MatteBlack) {
                BottomSquareButton("Days", R.drawable.outline_today_24) { }
                BottomSquareButton("Week", R.drawable.outline_view_week_24) { }
                BottomSquareButton("Reset", R.drawable.outline_calendar_month_24) { }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // 🔹 Bar chart with animation
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val values = listOf(50, 100, 250, 500, 200, 100, 50)
                values.forEach { value ->
                    val animatedHeight by animateDpAsState(
                        targetValue = (value / 2).dp,
                        label = "barHeight"
                    )
                    Box(
                        modifier = Modifier
                            .width(28.dp)
                            .height(animatedHeight)
                            .background(NeonPink, RoundedCornerShape(6.dp))
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🔹 Calories Info
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Calories", color = NeonGreen, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Heart",
                        tint = Color.Red
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("525", color = Color.White, style = MaterialTheme.typography.bodyLarge)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 🔹 Camera Button
            Button(
                onClick = { /* Open Camera */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NeonGreen),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_camera_alt_24),
                    contentDescription = "Camera",
                    tint = MatteBlack
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Camera", color = MatteBlack, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun RowScope.BottomSquareButton(
    text: String,
    @androidx.annotation.DrawableRes iconRes: Int,
    onClick: () -> Unit
) {
    val NeonGreen = Color(0xFF00FF66)
    val MatteBlack = Color(0xFF000000)

    NavigationBarItem(
        selected = false,
        onClick = onClick,
        icon = {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                tint = NeonGreen
            )
        },
        label = {
            Text(text, color = NeonGreen, fontWeight = FontWeight.Bold)
        },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = MatteBlack,
            selectedIconColor = NeonGreen,
            unselectedIconColor = NeonGreen
        )
    )
}
