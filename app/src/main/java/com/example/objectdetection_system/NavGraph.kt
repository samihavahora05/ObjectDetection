package com.example.objectdetection_system

import android.Manifest
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.objectdetection_system.ui.screens.AboutModelScreen
import com.example.objectdetection_system.ui.screens.DetectionScreen
import com.example.objectdetection_system.ui.screens.HomeScreen
import com.example.objectdetection_system.ui.screens.LogsScreen
import com.example.objectdetection_system.ui.screens.SettingsScreen
import com.example.objectdetection_system.ui.screens.SplashScreen
import com.example.objectdetection_system.ui.screens.StatsScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    hasCameraPermission: Boolean,
    cameraPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>
) {
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController)
        }

        composable("home") {
            HomeScreen(navController)
        }

        composable("detection") { // Note: Changed to lowercase "detection" to match your HomeScreen navigate call
            if (hasCameraPermission) {
                DetectionScreen(navController)
            } else {
                // If permission is missing, trigger the request when entering this screen
                SideEffect {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }
        }

        composable("about") { // Changed to "about" to match your navigate("about") calls
            AboutModelScreen(navController)
        }

        composable("stats") { StatsScreen(navController) }

        composable("logs") { LogsScreen(navController) }

        composable("settings") { SettingsScreen(navController) }
    }
}