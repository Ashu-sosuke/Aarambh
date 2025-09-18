package com.example.arambh


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(reps: Int, score: Int, onRestart: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Workout Results") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Workout Summary", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(24.dp))
            Text("Reps Completed: $reps", style = MaterialTheme.typography.bodyLarge)
            Text("Final Score: $score", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(32.dp))
            Button(onClick = { onRestart() }) {
                Text("Restart Workout")
            }
        }
    }
}
