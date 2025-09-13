package com.example.arambh

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ActivityBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Activities
        ActivityBox(
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Add button
        AddButton()
    }
}

@Composable
fun ActivityBox(modifier: Modifier = Modifier) {
    val itemList = listOf("Squat", "PushUp", "Plank", "Lunges")

    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 2 per row for bigger cards
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .height(200.dp)
            .background(Color.Black.copy(alpha = 0.05f), shape = MaterialTheme.shapes.medium)
            .padding(8.dp)
    ) {
        items(itemList) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun AddButton() {
    Card(
        modifier = Modifier
            .size(70.dp)
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF00C853)), // Green accent
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add Activity",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
