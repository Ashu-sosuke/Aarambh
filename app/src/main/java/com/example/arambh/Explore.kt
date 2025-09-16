package com.example.arambh

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExploreScreen() {
    // 🔹 Sports list
    val sports = listOf(
        "Wrestling", "Powerlifting",
        "Long Jump", "Pole Race",
        "Boxing", "Judo",
        "Karate", "Taekwondo",
        "Swimming", "Running",
        "High Jump", "Archery",
        "Football", "Basketball",
        "Tennis", "Cricket",
        "Cycling", "Rowing"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // 🔹 Dark background
            .padding(16.dp)
    ) {
        // 🔹 Top bar with back arrow + title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            IconButton(onClick = { /* Handle back navigation */ }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White // White arrow
                )
            }
            Text(
                text = "Explore",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White // White title
            )
        }

        // 🔹 Scrollable grid of sports
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(sports) { sport ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)) // Dark gray card
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = sport,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White // White text inside cards
                        )
                    }
                }
            }
        }
    }
}
