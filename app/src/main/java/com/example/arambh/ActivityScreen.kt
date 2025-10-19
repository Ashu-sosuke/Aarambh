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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun ActivityBar(
    selectedActivities: List<String>,
    onActivitiesChange: (List<String>) -> Unit,
    onNavigate: (String) -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var showDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    // ✅ Load saved data from Firestore on launch
    LaunchedEffect(Unit) {
        user?.let {
            val doc = db.collection("users").document(it.uid).get().await()
            val saved = doc.get("activities") as? List<String> ?: emptyList()
            onActivitiesChange(saved)
        }
        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color(0xFF00FF88),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SmallAddButton(onClick = { showDialog = true })
                Spacer(modifier = Modifier.width(12.dp))
                ActivityBox(
                    selectedActivities = selectedActivities,
                    onNavigate = onNavigate,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }

    if (showDialog) {
        ActivityDialog(
            initialSelection = selectedActivities,
            onDismiss = { showDialog = false },
            onActivitiesSelected = { activities ->
                onActivitiesChange(activities) // ✅ Notify parent
                showDialog = false

                user?.let { u ->
                    val uid = u.uid
                    db.collection("users").document(uid)
                        .update("activities", activities)
                        .addOnSuccessListener {
                            scope.launch {
                                snackbarHostState.showSnackbar("Activities updated successfully ✅")
                            }
                        }
                        .addOnFailureListener {
                            db.collection("users").document(uid)
                                .set(mapOf("activities" to activities))
                                .addOnSuccessListener {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Activities saved successfully ✅")
                                    }
                                }
                                .addOnFailureListener { e ->
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Error: ${e.message}")
                                    }
                                }
                        }
                }
            }
        )
    }
}

@Composable
fun ActivityDialog(
    initialSelection: List<String>,
    onDismiss: () -> Unit,
    onActivitiesSelected: (List<String>) -> Unit
) {
    val activities = listOf("Squat", "PushUp", "Plank", "Lunges", "Burpees", "Crunches")
    var selected by remember { mutableStateOf(initialSelection.toSet()) }

    AlertDialog(
        onDismissRequest = onDismiss,
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
                    text = "Choose Activities",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                activities.forEach { activity ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selected =
                                    if (selected.contains(activity)) selected - activity
                                    else selected + activity
                            }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selected.contains(activity),
                            onCheckedChange = {
                                selected =
                                    if (it) selected + activity else selected - activity
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

@Composable
fun ActivityBox(
    selectedActivities: List<String>,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(selectedActivities.size) { index ->
            val item = selectedActivities[index]

            Card(
                modifier = Modifier
                    .wrapContentWidth()
                    .clickable { onNavigate(item) },
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_camera_alt_24),
                        contentDescription = "Activity Icon",
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
fun SmallAddButton(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(50.dp)
            .padding(4.dp)
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
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
