package com.example.objectdetection_system

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val navController = rememberNavController()

            // 1. Track permission state
            var hasCameraPermission by remember {
                mutableStateOf(
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                )
            }

            // 2. Define the launcher to ask for permission
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                    hasCameraPermission = isGranted
                }
            )

            // 3. Ask for permission as soon as the app opens
            LaunchedEffect(Unit) {
                if (!hasCameraPermission) {
                    launcher.launch(Manifest.permission.CAMERA)
                }
            }

            // 4. Pass EVERYTHING to the NavGraph
            NavGraph(
                navController = navController,
                hasCameraPermission = hasCameraPermission,
                cameraPermissionLauncher = launcher
            )
        }
    }
}