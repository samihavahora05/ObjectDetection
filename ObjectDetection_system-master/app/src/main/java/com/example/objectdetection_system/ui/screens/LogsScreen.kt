package com.example.objectdetection_system.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class DetectionLog(val title: String, val time: String, val confidence: String)

@Composable
fun LogsScreen(navController: NavController) {
    val accentBlue = Color(0xFF007BFF)
    val darkBg = Color(0xFF020408)

    val logs = listOf(
        DetectionLog("Person Detected", "12:45 PM", "99.2%"),
        DetectionLog("Laptop / MacBook", "11:20 AM", "88.5%"),
        DetectionLog("Cell Phone", "10:15 AM", "94.1%"),
        DetectionLog("Desk Lamp", "09:30 AM", "72.0%"),
        DetectionLog("Backpack", "08:12 AM", "91.4%")
    )

    Column(modifier = Modifier.fillMaxSize().background(darkBg).statusBarsPadding()) {
        Row(
            Modifier.fillMaxWidth().padding(28.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("DETECTION_LOGS", color = accentBlue, fontSize = 10.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp)
            Icon(Icons.Rounded.Search, null, tint = Color.Gray)
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(logs) { log ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFF0D121A),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            Modifier.size(44.dp).background(accentBlue.copy(0.1f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Rounded.History, null, tint = accentBlue, modifier = Modifier.size(20.dp))
                        }

                        Spacer(Modifier.width(16.dp))

                        Column(Modifier.weight(1f)) {
                            Text(log.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text("Confidence: ${log.confidence}", color = Color.Gray, fontSize = 12.sp)
                        }

                        Text(log.time, color = accentBlue, fontSize = 10.sp, fontWeight = FontWeight.Black)
                    }
                }
            }
        }
    }
}