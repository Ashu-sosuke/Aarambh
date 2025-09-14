package com.example.arambh

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SquatScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.clickable { /* handle back */ }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text("Squat", style = MaterialTheme.typography.titleLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(200.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val values = listOf(50, 100, 250, 500, 200, 100, 50)
            values.forEach { value ->
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height((value / 2).dp) // scale height
                        .background(Color.Magenta, RoundedCornerShape(4.dp))
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Calories", style = MaterialTheme.typography.bodyMedium)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Heart",
                tint = Color.Red
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("525")
        }

        Spacer(modifier = Modifier.height(30.dp))

        // 🔹 Camera Button
        Button(
            onClick = { /* Open Camera */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_camera_alt_24),
                contentDescription = "Camera"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Camera")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { /* Days */ }) { Text("Days") }
            Button(onClick = { /* Week */ }) { Text("Week") }
            Button(onClick = { /* Reset */ }) { Text("Reset") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSquatScreen() {
    SquatScreen()
}