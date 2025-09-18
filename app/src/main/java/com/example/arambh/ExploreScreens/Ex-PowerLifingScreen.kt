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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun ExPowerLiftingScreen(navController: NavController) {

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
                    Text("PowerLifting",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White)},
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
                .padding(12.dp)
        ) {

            repeat(3) {
                RecordCard(recordText = "Record: 100kg", likes = 120, comments = 45)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Composable
fun RecordCard(
    recordText: String,
    likes: Int,
    comments: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)), // dark gray card
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Row: Thumbnail + record text
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.DarkGray, shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Photo,
                        contentDescription = "Thumbnail",
                        tint = Color.LightGray,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = recordText,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Row: Like, Comment, Share with counts
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "Like",
                        tint = Color(0xFFE91E63), // pink
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = likes.toString(),
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Filled.ChatBubble,
                        contentDescription = "Comment",
                        tint = Color(0xFF90CAF9), // light blue
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = comments.toString(),
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Filled.Share,
                        contentDescription = "Share",
                        tint = Color(0xFF81C784), // green
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Share",
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
