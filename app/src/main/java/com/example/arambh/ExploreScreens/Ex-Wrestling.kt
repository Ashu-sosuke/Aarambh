package com.example.arambh.ExploreScreens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.arambh.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExWrestlingScreen(navController: NavController) {

    val context = LocalContext.current
    var videoUri by remember { mutableStateOf<Uri?>(null) }

    // --- Launcher for capturing video ---
    val videoCaptureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            videoUri = result.data?.data
        }
    }

    // --- Launcher for requesting permissions ---
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val cameraGranted = permissions[android.Manifest.permission.CAMERA] ?: false
        val audioGranted = permissions[android.Manifest.permission.RECORD_AUDIO] ?: false
        if (cameraGranted && audioGranted) {
            // Both granted → launch system camera to record video
            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            videoCaptureLauncher.launch(intent)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Wrestling",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Screen.HomeScreen.route) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Ask for CAMERA + RECORD_AUDIO permissions first
                    permissionLauncher.launch(
                        arrayOf(
                            android.Manifest.permission.CAMERA,
                            android.Manifest.permission.RECORD_AUDIO
                        )
                    )
                },
                shape = CircleShape,
                containerColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.PhotoCamera,
                    contentDescription = "Camera",
                    tint = Color.Black
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingValues)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(3) {
                RecordCard(recordText = "Record: 100kg", likes = 120, comments = 45)
                Spacer(modifier = Modifier.height(16.dp))
            }

            videoUri?.let {
                Text(
                    text = "Video Recorded: $it",
                    color = Color.Green,
                    fontSize = 14.sp
                )
            }
        }
    }
}
