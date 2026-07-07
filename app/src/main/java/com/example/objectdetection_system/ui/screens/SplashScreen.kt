package com.example.objectdetection_system.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val accentBlue = Color(0xFF007BFF)
    val darkBg = Color(0xFF020408)

    // Animations
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.2f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse), label = "scale"
    )

    var progress by remember { mutableStateOf(0f) }
    var statusText by remember { mutableStateOf("INITIALIZING_ENGINE...") }

    // Logic to simulate model loading
    LaunchedEffect(Unit) {
        delay(500)
        progress = 0.3f
        statusText = "LOADING_YOLO_V8_WEIGHTS..."
        delay(800)
        progress = 0.6f
        statusText = "OPTIMIZING_GPU_PIPELINE..."
        delay(800)
        progress = 0.9f
        statusText = "NEURAL_LINK_ESTABLISHED"
        delay(500)
        navController.navigate("home") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // --- 1. PULSING AI EYE ---
        Box(contentAlignment = Alignment.Center) {
            // Outer Glow
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .scale(scale)
                    .alpha(0.3f)
                    .background(accentBlue, CircleShape)
            )
            // Core Icon
            Surface(
                modifier = Modifier.size(80.dp),
                color = accentBlue,
                shape = CircleShape,
                shadowElevation = 20.dp
            ) {
                Icon(
                    Icons.Rounded.Visibility,
                    null,
                    tint = Color.White,
                    modifier = Modifier.padding(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // --- 2. BRANDING ---
        Text(
            "OBJECTVISION",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 4.sp
        )
        Text(
            "AI_MODEL_v4.2",
            color = accentBlue,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(60.dp))

        // --- 3. PROGRESS BAR ---
        Column(
            modifier = Modifier.padding(horizontal = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = accentBlue,
                trackColor = Color.White.copy(0.1f),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                statusText,
                color = Color.Gray,
                fontSize = 9.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp
            )
        }
    }
}