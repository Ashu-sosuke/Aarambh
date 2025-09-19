package com.example.arambh

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.max

@Composable
fun StepCounterUI(
    steps: Int,
    target: Int,
    modifier: Modifier = Modifier
) {
    val rawProgress = (steps.toFloat() / target.toFloat()).coerceIn(0f, 1f)

    val animatedProgress by animateFloatAsState(
        targetValue = rawProgress,
        animationSpec = tween(durationMillis = 800),
        label = "progressAnim"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(8.dp)
            .aspectRatio(1f)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = size.minDimension * 0.15f
            val inset = strokeWidth / 2f  // ✅ shift arcs inward

            val diameter = size.minDimension - strokeWidth
            val topLeft = Offset(inset, inset) // ✅ inset on all sides
            val arcSize = Size(diameter, diameter)

            // Background arc
            drawArc(
                color = Color.White,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(strokeWidth, cap = StrokeCap.Round)
            )

            // Progress arc
            drawArc(
                color = Color(0xFFFF4A91),
                startAngle = 180f,
                sweepAngle = 180f * animatedProgress,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(strokeWidth, cap = StrokeCap.Round)
            )
        }

        // ✅ Move text down into semi-circle space
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp), // adjust padding inside arc
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = steps.toString(),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Target: $target",
                color = Color.LightGray,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun StepCounterGrid(
    counters: List<Pair<Int, Int>>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // ✅ show 2 per row (bigger semicircle)
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 0.dp, max = 220.dp), // more height
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(counters) { (steps, target) ->
            StepCounterUI(
                steps = steps,
                target = target,
                modifier = Modifier.size(160.dp) // ✅ increase size
            )
        }
    }
}
