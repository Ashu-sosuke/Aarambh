package com.example.arambh

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


val sportsData = mapOf(
    "Powerlifting" to SportInfo(
        route = Screen.PowerLifting.route,
        imageRes = R.drawable.download
    ),
    "Wrestling" to SportInfo(
        route = Screen.WreasVideoScr.route,
        imageRes = R.drawable.save__wrestling__savewrestling_for_olympic__lobbying_to_save_olympic_wrestling
    ),

)

data class SportInfo(
    val route: String,
    val imageRes: Int
)

@Composable
fun SportsBar(onNavigate: (String) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedActivities by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            LargeAddButton(onClick = { showDialog = true })

            Spacer(modifier = Modifier.width(16.dp))

            SportsBox(
                selectedActivities = selectedActivities,
                onNavigate = { sportName ->
                    sportsData[sportName]?.let { sport ->
                        onNavigate(sport.route)
                    }
                },
                modifier = Modifier.weight(1f)
            )
        }
    }

    if (showDialog) {
        SportDialog(
            initialSelection = selectedActivities,
            onDismiss = { showDialog = false },
            onActivitiesSelected = { activities ->
                selectedActivities = activities
                showDialog = false
            }
        )
    }
}

// -----------------------------
// 3. SportsBox
// -----------------------------
@Composable
fun SportsBox(
    selectedActivities: List<String>,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val filtered = selectedActivities.filter { it in sportsData.keys }

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(filtered.size) { index ->
            val sportName = filtered[index]
            val sportInfo = sportsData[sportName]
            if (sportInfo != null) {
                SportCard(
                    imageRes = sportInfo.imageRes,
                    text = sportName,
                    onClick = { onNavigate(sportName) }
                )
            }
        }
    }
}

// -----------------------------
// 4. SportDialog
// -----------------------------
@Composable
fun SportDialog(
    initialSelection: List<String>,
    onDismiss: () -> Unit,
    onActivitiesSelected: (List<String>) -> Unit
) {
    var selected by remember { mutableStateOf(initialSelection.toSet()) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = { onActivitiesSelected(selected.toList()) }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        text = {
            Column {
                Text(
                    text = "Choose Sports",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                sportsData.keys.forEach { activity ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selected = if (selected.contains(activity)) {
                                    selected - activity
                                } else {
                                    selected + activity
                                }
                            }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selected.contains(activity),
                            onCheckedChange = {
                                selected = if (it) selected + activity else selected - activity
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(activity, fontSize = 16.sp)
                    }
                }
            }
        }
    )
}

// -----------------------------
// 5. SportCard
// -----------------------------
@Composable
fun SportCard(
    imageRes: Int,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .size(width = 140.dp, height = 160.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = imageRes),
                contentDescription = text,
                modifier = Modifier.size(64.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}

// -----------------------------
// 6. Large Add Button
// -----------------------------
@Composable
fun LargeAddButton(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(100.dp)
            .clickable { onClick() },
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
                modifier = Modifier.size(36.dp)
            )
        }
    }
}
