package com.example.objectdetection_system.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    val accentBlue = Color(0xFF007BFF)
    val darkBg = Color(0xFF020408)
    val surfaceColor = Color(0xFF0D121A)

    Scaffold(
        containerColor = darkBg,
        bottomBar = { FloatingProNav(navController, "home") }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // --- 1. USER HUD HEADER ---
            Row(
                modifier = Modifier.fillMaxWidth().padding(28.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("NEURAL_LINK_ACTIVE", color = accentBlue, fontSize = 10.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp)
                    Text("Welcome back, User", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier.size(48.dp).background(surfaceColor, CircleShape).border(1.dp, Color.White.copy(0.1f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Rounded.NotificationsActive, null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
            }

            // --- 2. MAIN ANALYTICS CARD ---
            Box(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).height(200.dp).clip(RoundedCornerShape(32.dp))
                    .background(Brush.verticalGradient(listOf(accentBlue.copy(0.2f), surfaceColor)))
                    .border(1.dp, Color.White.copy(0.05f), RoundedCornerShape(32.dp))
            ) {
                Column(Modifier.padding(24.dp)) {
                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                        Text("NETWORK PERFORMANCE", color = Color.White.copy(0.5f), fontSize = 10.sp, fontWeight = FontWeight.Black)
                        Icon(Icons.Rounded.Timeline, null, tint = accentBlue)
                    }
                    Spacer(Modifier.height(12.dp))
                    Text("98.4%", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Black)
                    Text("Detection Precision Rate", color = Color.Gray, fontSize = 12.sp)

                    Spacer(Modifier.weight(1f))
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        repeat(15) { index ->
                            Box(Modifier.width(12.dp).height((20..60).random().dp).background(if (index > 10) Color.Gray else accentBlue, RoundedCornerShape(4.dp)))
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // --- 3. CORE ACTION BUTTONS ---
            Column(modifier = Modifier.padding(horizontal = 24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

                // PRIMARY: START DETECTION
                Surface(
                    onClick = { navController.navigate("detection") },
                    modifier = Modifier.fillMaxWidth().height(80.dp),
                    color = accentBlue,
                    shape = RoundedCornerShape(24.dp),
                    shadowElevation = 8.dp
                ) {
                    Row(Modifier.padding(horizontal = 24.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(44.dp).background(Color.White.copy(0.2f), CircleShape), contentAlignment = Alignment.Center) {
                            Icon(Icons.Rounded.CenterFocusStrong, null, tint = Color.White)
                        }
                        Spacer(Modifier.width(16.dp))
                        Column(Modifier.weight(1f)) {
                            Text("START OBJECT DETECTION", color = Color.White, fontWeight = FontWeight.Black, fontSize = 15.sp)
                            Text("Initialize real-time neural scan", color = Color.White.copy(0.7f), fontSize = 11.sp)
                        }
                        Icon(Icons.AutoMirrored.Rounded.ArrowForward, null, tint = Color.White)
                    }
                }

                // SECONDARY: ABOUT MODEL (NEW BUTTON BELOW)
                OutlinedButton(
                    onClick = { navController.navigate("about") },
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, Color.White.copy(0.1f)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                ) {
                    Icon(Icons.Rounded.Info, null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(12.dp))
                    Text("VIEW MODEL ARCHITECTURE", fontSize = 13.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                }
            }

            Spacer(Modifier.height(32.dp))

            // --- 4. ENGINE DIAGNOSTICS ---
            Text("ENGINE DIAGNOSTICS", modifier = Modifier.padding(horizontal = 28.dp), color = Color.White.copy(0.4f), fontSize = 10.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp)

            Spacer(Modifier.height(16.dp))

            Row(Modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                DetailCard("Model", "YOLOv8-N", Icons.Rounded.Memory, Modifier.weight(1f))
                DetailCard("Latency", "12.4ms", Icons.Rounded.Speed, Modifier.weight(1f))
            }

            Spacer(Modifier.height(16.dp))

            Row(Modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                DetailCard("Dataset", "COCO v2", Icons.Rounded.Storage, Modifier.weight(1f))
                DetailCard("Status", "Optimized", Icons.Rounded.CheckCircle, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

// --- SUB-COMPOSABLES ---

@Composable
fun DetailCard(label: String, value: String, icon: ImageVector, modifier: Modifier) {
    Surface(
        modifier = modifier,
        color = Color(0xFF0D121A),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, Color.White.copy(0.05f))
    ) {
        Column(Modifier.padding(20.dp)) {
            Icon(icon, null, tint = Color(0xFF007BFF), modifier = Modifier.size(20.dp))
            Spacer(Modifier.height(12.dp))
            Text(label, color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(value, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
fun FloatingProNav(navController: NavController, currentRoute: String) {
    Surface(
        modifier = Modifier.padding(24.dp).height(68.dp).fillMaxWidth(),
        color = Color(0xFF161B22).copy(0.95f),
        shape = CircleShape,
        border = BorderStroke(1.dp, Color.White.copy(0.1f))
    ) {
        Row(Modifier.fillMaxSize(), Arrangement.SpaceEvenly, Alignment.CenterVertically) {
            NavIconDetailed(Icons.Rounded.Home, "Home", currentRoute == "home") {
                if (currentRoute != "home") navController.navigate("home") { popUpTo("home") { inclusive = true } }
            }
            NavIconDetailed(Icons.Rounded.BarChart, "Stats", currentRoute == "stats") {
                if (currentRoute != "stats") navController.navigate("stats")
            }
            NavIconDetailed(Icons.Rounded.History, "Logs", currentRoute == "logs") {
                if (currentRoute != "logs") navController.navigate("logs")
            }
            NavIconDetailed(Icons.Rounded.Settings, "Set", currentRoute == "settings") {
                if (currentRoute != "settings") navController.navigate("settings")
            }
        }
    }
}

@Composable
fun NavIconDetailed(icon: ImageVector, label: String, active: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clip(CircleShape).clickable(onClick = onClick).padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, null, tint = if (active) Color(0xFF007BFF) else Color.Gray, modifier = Modifier.size(24.dp))
        Text(label, fontSize = 9.sp, color = if (active) Color.White else Color.Gray, fontWeight = FontWeight.Bold)
    }
}