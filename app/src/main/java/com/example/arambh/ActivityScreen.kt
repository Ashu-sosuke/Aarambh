import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.arambh.R

@Composable
fun ActivityBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Smaller Add button on left
        SmallAddButton()

        Spacer(modifier = Modifier.width(12.dp))

        // Activities on right (wrap in rows)
        ActivityBox(
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ActivityBox(modifier: Modifier = Modifier) {
    val itemList = listOf("Squat", "PushUp", "Plank", "Lunges", "Burpees", "Crunches")

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(end = 16.dp) // padding at end
    ) {
        items(itemList.size) { index ->
            val item = itemList[index]

            Card(
                modifier = Modifier.wrapContentWidth()
                    .clickable{
                    //TODO
                },
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_camera_alt_24),
                        contentDescription = "Camera",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
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
fun SmallAddButton() {
    Card(
        modifier = Modifier
            .size(50.dp) // smaller size
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF00C853)),
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
                modifier = Modifier.size(24.dp)
                    .clickable{
                        //TODO
                    },
            )
        }
    }
}
