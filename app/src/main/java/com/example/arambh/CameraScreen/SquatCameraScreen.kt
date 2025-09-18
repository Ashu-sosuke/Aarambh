package com.example.arambh.CameraScreen

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.OptIn
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.accurate.AccuratePoseDetectorOptions
import java.util.concurrent.Executors
import com.google.mlkit.vision.pose.PoseDetector


@SuppressLint("UnsafeOptInUsageError")
@Composable
fun SquatCameraScreen(onFinish: (Int, Int) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    var reps by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var isSquatting by remember { mutableStateOf(false) }

    var landmarks by remember { mutableStateOf<List<PoseLandmark>>(emptyList()) }
    var imageWidth by remember { mutableStateOf(1) }
    var imageHeight by remember { mutableStateOf(1) }

    val poseDetector = remember {
        val options = AccuratePoseDetectorOptions.Builder()
            .setDetectorMode(AccuratePoseDetectorOptions.STREAM_MODE)
            .build()
        PoseDetection.getClient(options)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Camera Preview
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()
                    val preview = Preview.Builder().build().apply {
                        setSurfaceProvider(previewView.surfaceProvider)
                    }

                    val imageAnalyzer = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also {
                            it.setAnalyzer(cameraExecutor) { imageProxy ->
                                processImageProxy(
                                    imageProxy,
                                    poseDetector,
                                    onPoseDetected = { isDown ->
                                        if (isDown && !isSquatting) {
                                            isSquatting = true
                                        } else if (!isDown && isSquatting) {
                                            reps++
                                            score = reps * 10
                                            isSquatting = false
                                        }
                                    },
                                    onLandmarks = { detected, width, height ->
                                        landmarks = detected
                                        imageWidth = width
                                        imageHeight = height
                                    }
                                )
                            }
                        }

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            CameraSelector.DEFAULT_BACK_CAMERA, // 👈 back camera
                            preview,
                            imageAnalyzer
                        )
                    } catch (e: Exception) {
                        Log.e("CameraScreen", "Use case binding failed", e)
                    }
                }, ContextCompat.getMainExecutor(ctx))
                previewView
            },
            modifier = Modifier.fillMaxSize()
        )

        // Skeleton overlay
        Canvas(modifier = Modifier.fillMaxSize()) {
            if (landmarks.isNotEmpty()) {
                val scaleX = size.width / imageWidth.toFloat()
                val scaleY = size.height / imageHeight.toFloat()

                // Draw landmarks (red dots)
                landmarks.forEach { landmark ->
                    val x = landmark.position.x * scaleX
                    val y = landmark.position.y * scaleY
                    drawCircle(
                        color = Color.Red,
                        radius = 6f,
                        center = Offset(x, y)
                    )
                }

                // Draw connections (green lines)
                fun connect(start: Int, end: Int) {
                    val p1 = landmarks.find { it.landmarkType == start }
                    val p2 = landmarks.find { it.landmarkType == end }
                    if (p1 != null && p2 != null) {
                        drawLine(
                            color = Color.Green,
                            start = Offset(p1.position.x * scaleX, p1.position.y * scaleY),
                            end = Offset(p2.position.x * scaleX, p2.position.y * scaleY),
                            strokeWidth = 3f
                        )
                    }
                }

                // Arms
                connect(PoseLandmark.LEFT_SHOULDER, PoseLandmark.LEFT_ELBOW)
                connect(PoseLandmark.LEFT_ELBOW, PoseLandmark.LEFT_WRIST)
                connect(PoseLandmark.RIGHT_SHOULDER, PoseLandmark.RIGHT_ELBOW)
                connect(PoseLandmark.RIGHT_ELBOW, PoseLandmark.RIGHT_WRIST)

                // Legs
                connect(PoseLandmark.LEFT_HIP, PoseLandmark.LEFT_KNEE)
                connect(PoseLandmark.LEFT_KNEE, PoseLandmark.LEFT_ANKLE)
                connect(PoseLandmark.RIGHT_HIP, PoseLandmark.RIGHT_KNEE)
                connect(PoseLandmark.RIGHT_KNEE, PoseLandmark.RIGHT_ANKLE)

                // Body
                connect(PoseLandmark.LEFT_SHOULDER, PoseLandmark.RIGHT_SHOULDER)
                connect(PoseLandmark.LEFT_HIP, PoseLandmark.RIGHT_HIP)
                connect(PoseLandmark.LEFT_SHOULDER, PoseLandmark.LEFT_HIP)
                connect(PoseLandmark.RIGHT_SHOULDER, PoseLandmark.RIGHT_HIP)
            }
        }

        // Bottom controls
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Reps: $reps", style = MaterialTheme.typography.bodyLarge)
            Text("Score: $score", style = MaterialTheme.typography.bodyLarge)
            Button(onClick = { onFinish(reps, score) }) {
                Text("Finish")
            }
        }
    }
}

@OptIn(ExperimentalGetImage::class)
private fun processImageProxy(
    imageProxy: ImageProxy,
    detector: PoseDetector,
    onPoseDetected: (Boolean) -> Unit,
    onLandmarks: (List<PoseLandmark>, Int, Int) -> Unit
) {
    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        detector.process(image)
            .addOnSuccessListener { pose: Pose ->
                val leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE)
                val leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP)

                if (leftKnee != null && leftHip != null) {
                    val isDown = leftHip.position.y - leftKnee.position.y > 50
                    onPoseDetected(isDown)
                }

                // send landmarks to UI
                onLandmarks(pose.allPoseLandmarks, mediaImage.width, mediaImage.height)
            }
            .addOnCompleteListener {
                imageProxy.close()
            }

    } else {
        imageProxy.close()
    }
}
