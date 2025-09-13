package com.example.arambh

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserDashScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Avatar Box
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color(0xFF6C63FF).copy(alpha = 0.3f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Avatar",
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                StatBar(
                    label = "Health",
                    value = 50,
                    max = 50,
                    color = Color.Red,
                    icon = Icons.Default.Favorite
                )

                Spacer(modifier = Modifier.height(8.dp))

                StatBar(
                    label = "Experience",
                    value = 19,
                    max = 25,
                    color = Color(0xFFFFC107),
                    icon = Icons.Default.Star
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = "Locked",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Unlock by reaching level 10",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}


@Composable
fun StatBar(
    label: String,
    value: Int,
    max: Int,
    color: Color,
    icon: ImageVector
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = label, tint = color)
            Spacer(modifier = Modifier.width(4.dp))
            Text("$value / $max", color = Color.White, fontSize = 14.sp)
            Spacer(modifier = Modifier.weight(1f))
            Text(label, color = Color.Gray, fontSize = 14.sp)
        }
        LinearProgressIndicator(
            progress = { value / max.toFloat() },
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(50)),
            color = color,
            trackColor = Color.DarkGray,
            strokeCap = ProgressIndicatorDefaults.LinearStrokeCap
        )
    }
}
