package com.example.objectdetection_system.ui.screens
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Hub
import androidx.compose.material.icons.rounded.Memory
import androidx.compose.material.icons.rounded.Schema
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AboutModelScreen(navController: NavController) {
    val accentBlue = Color(0xFF007BFF)
    val darkBg = Color(0xFF020408)
    val surfaceColor = Color(0xFF0D121A)
    val sam = Color(0xFF007BFF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBg)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        // --- 1. HEADER ---
        Row(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.background(Color.White.copy(0.05f), CircleShape)
            ) {
                Icon(Icons.AutoMirrored.Rounded.ArrowBack, "Back", tint = Color.White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text("MODEL_ARCHITECTURE", color = accentBlue, fontSize = 10.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp)
        }

        // --- 2. NEURAL NETWORK VISUALIZATION ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(240.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(surfaceColor)
                .border(1.dp, Color.White.copy(0.05f), RoundedCornerShape(32.dp)),
            contentAlignment = Alignment.Center
        ) {
            NeuralNetworkCanvas(accentBlue) // Custom technical drawing

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    color = accentBlue.copy(0.1f),
                    shape = CircleShape,
                    border = BorderStroke(1.dp, accentBlue.copy(0.4f))
                ) {
                    Text(
                        "YOLOv8 NANO ENGINE",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        color = accentBlue,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }

        // --- 3. CORE SPECS ---
        Column(Modifier.padding(28.dp)) {
            Text("Real-Time Engine", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Black)
            Spacer(Modifier.height(12.dp))
            Text(
                "Our neural engine is optimized for mobile hardware acceleration. It processes 640x640 images through 225 layers of deep learning nodes to predict object boundaries with millisecond precision.",
                color = Color.Gray, fontSize = 14.sp, lineHeight = 22.sp
            )
        }

        // --- 4. DETAILED SPEC CARDS ---
        Column(Modifier.padding(horizontal = 24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            AboutSpecCard("Architecture", "Darknet-53 Inspired", Icons.Rounded.Schema, accentBlue)
            AboutSpecCard("Tensor Type", "INT8 Quantized", Icons.Rounded.Memory, accentBlue)
            AboutSpecCard("Processing", "GPU Accelerated", Icons.Rounded.Hub, accentBlue)
        }

        Spacer(Modifier.height(40.dp))
        Text(
            "V4.2.0 STABLE RELEASE\nNEURAL LINK SYSTEMS © 2026",
            modifier = Modifier.fillMaxWidth().alpha(0.3f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        Spacer(Modifier.height(40.dp))
    }
}

@Composable
fun NeuralNetworkCanvas(accent: Color) {
    Canvas(modifier = Modifier.fillMaxSize().alpha(0.2f)) {
        val nodes = listOf(
            Offset(size.width * 0.2f, size.height * 0.3f),
            Offset(size.width * 0.2f, size.height * 0.7f),
            Offset(size.width * 0.5f, size.height * 0.5f),
            Offset(size.width * 0.8f, size.height * 0.3f),
            Offset(size.width * 0.8f, size.height * 0.7f)
        )

        // Draw Connections
        nodes.forEach { start ->
            nodes.forEach { end ->
                if (start != end) {
                    drawLine(color = accent, start = start, end = end, strokeWidth = 1f)
                }
            }
        }

        // Draw Nodes
        nodes.forEach { pos ->
            drawCircle(color = accent, radius = 6.dp.toPx(), center = pos)
            drawCircle(color = Color.White, radius = 2.dp.toPx(), center = pos)
        }
    }
}

@Composable
fun AboutSpecCard(label: String, value: String, icon: ImageVector, accent: Color) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFF0D121A),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color.White.copy(0.05f))
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(40.dp).background(accent.copy(0.1f), CircleShape), contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = accent, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(label, color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Text(value, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Black)
            }
        }
    }
}