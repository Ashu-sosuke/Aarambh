package com.example.arambh

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ActivityCount(selectedActivities: List<String>) {

    val activityIcons = mapOf(
        "Squat" to R.drawable.icons8_squats_skin_type_1_48,
        "PushUp" to R.drawable.boy_doing_pushups_,
        "Plank" to R.drawable.icons8_plank_50,
        "Lunges" to R.drawable.icons8_lunges_50,
        "Burpees" to R.drawable.icons8_plank_50,
        "Crunches" to R.drawable.icons8_sit_ups_30
    )

    val activityProgress = mapOf(
        "Squat" to "3/20",
        "PushUp" to "10/30",
        "Plank" to "2/10",
        "Lunges" to "4/25",
        "Burpees" to "1/15",
        "Crunches" to "5/20"
    )

    if (selectedActivities.isEmpty()) {
        Text(
            text = "No activities selected",
            color = Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier.padding(8.dp)
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(selectedActivities.size) { index ->
                    val activity = selectedActivities[index]
                    val iconRes = activityIcons[activity] ?: R.drawable.baseline_fitness_center_24
                    val progress = activityProgress[activity] ?: "0/0"

                    Card(
                        modifier = Modifier
                            .wrapContentSize(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 10.dp)
                                .width(80.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(id = iconRes),
                                contentDescription = activity,
                                tint = Color(0xFF00FF88),
                                modifier = Modifier.size(36.dp)
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            // ✅ Activity name
                            Text(
                                text = activity,
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            // ✅ Record (progress)
                            Text(
                                text = progress,
                                color = Color(0xFF00FF88),
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}
