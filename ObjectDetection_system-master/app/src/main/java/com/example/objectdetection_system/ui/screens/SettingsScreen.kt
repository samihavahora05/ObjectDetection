package com.example.objectdetection_system.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BatteryFull
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.CloudOff
import androidx.compose.material.icons.rounded.Vibration
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SettingsScreen(navController: NavController) {
    val accentBlue = Color(0xFF007BFF)

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF020408)).statusBarsPadding()) {
        Text(
            text = "SYSTEM_CONFIGURATION",
            modifier = Modifier.padding(28.dp),
            color = accentBlue,
            fontSize = 10.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp
        )

        Column(Modifier.padding(horizontal = 24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("NEURAL ENGINE", color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Bold)

            SettingToggle("High Precision Mode", "Prioritize accuracy over speed", Icons.Rounded.Bolt, true, accentBlue)
            SettingToggle("Offline Processing", "No data leaves the device", Icons.Rounded.CloudOff, true, accentBlue)

            Spacer(Modifier.height(16.dp))
            Text("HARDWARE", color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Bold)

            SettingToggle("Haptic Feedback", "Vibrate on object detection", Icons.Rounded.Vibration, false, accentBlue)
            SettingToggle("Battery Optimization", "Lower FPS when battery < 20%", Icons.Rounded.BatteryFull, true, accentBlue)
        }
    }
}

@Composable
fun SettingToggle(title: String, desc: String, icon: ImageVector, initialState: Boolean, accent: Color) {
    var isChecked by remember { mutableStateOf(initialState) }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFF0D121A),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = Color.White, modifier = Modifier.size(24.dp))

            Spacer(Modifier.width(16.dp))

            Column(Modifier.weight(1f)) {
                Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(desc, color = Color.Gray, fontSize = 11.sp)
            }

            Switch(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = accent,
                    uncheckedTrackColor = Color.DarkGray
                )
            )
        }
    }
}