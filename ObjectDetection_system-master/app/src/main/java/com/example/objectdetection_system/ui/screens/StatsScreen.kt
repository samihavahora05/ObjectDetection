package com.example.objectdetection_system.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.Memory
import androidx.compose.material.icons.rounded.Speed
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun StatsScreen(navController: NavController) {
    val accentBlue = Color(0xFF007BFF)
    val darkBg = Color(0xFF020408)
    val cardBg = Color(0xFF0D121A)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBg)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        // --- HEADER ---
        Text(
            text = "NEURAL_ANALYTICS",
            modifier = Modifier.padding(28.dp),
            color = accentBlue,
            fontSize = 10.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp
        )

        // --- PERFORMANCE GRAPH CARD ---
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(260.dp),
            color = cardBg,
            shape = RoundedCornerShape(32.dp),
            border = BorderStroke(1.dp, Color.White.copy(0.05f))
        ) {
            Column(Modifier.padding(24.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("Inference Speed", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("Measured in milliseconds", color = Color.Gray, fontSize = 12.sp)
                    }
                    Icon(Icons.Rounded.Speed, null, tint = accentBlue)
                }

                Spacer(Modifier.weight(1f))

                // --- CUSTOM BAR CHART ---
                Row(
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val graphData = listOf(40, 70, 50, 90, 100, 80, 60, 85, 45, 110)
                    graphData.forEach { heightValue ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(heightValue.dp)
                                .background(
                                    brush = Brush.verticalGradient(listOf(accentBlue, accentBlue.copy(0.3f))),
                                    shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                                )
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))
                Text("AVERAGE LATENCY: 12.4ms", color = accentBlue, fontSize = 11.sp, fontWeight = FontWeight.Black)
            }
        }

        Spacer(Modifier.height(24.dp))

        // --- DETAILED METRICS GRID ---
        Column(Modifier.padding(horizontal = 24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                MetricBox("TOTAL SCANS", "14,209", Icons.Rounded.Analytics, Modifier.weight(1f), accentBlue)
                MetricBox("UPTIME", "128h", Icons.Rounded.Timer, Modifier.weight(1f), accentBlue)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                MetricBox("CPU LOAD", "24%", Icons.Rounded.Memory, Modifier.weight(1f), accentBlue)
                MetricBox("ACCURACY", "98.8%", Icons.Rounded.Speed, Modifier.weight(1f), accentBlue)
            }
        }

        Spacer(Modifier.height(100.dp)) // Nav Space
    }
}

@Composable
fun MetricBox(label: String, value: String, icon: ImageVector, modifier: Modifier, color: Color) {
    Surface(
        modifier = modifier,
        color = Color(0xFF0D121A),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, Color.White.copy(0.05f))
    ) {
        Column(Modifier.padding(20.dp)) {
            Icon(icon, null, tint = color, modifier = Modifier.size(20.dp))
            Spacer(Modifier.height(12.dp))
            Text(label, color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(value, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Black)
        }
    }
}