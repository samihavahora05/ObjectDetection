package com.example.objectdetection_system.ui.screens

import android.graphics.Rect
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController

@Composable
fun DetectionScreen(navController: NavController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val accentBlue = Color(0xFF007BFF)

    // State to hold our bounding boxes from the ML model
    var detectedObjects by remember { mutableStateOf<List<Rect>>(emptyList()) }

    //online detection mode
    var isOnlineMode by remember { mutableStateOf(false) }

    // --- 1. CAMERA CONTROLLER SETUP ---
    val cameraController = remember {
        LifecycleCameraController(context).apply {
            bindToLifecycle(lifecycleOwner)

            // ADD THIS: The engine that grabs frames for the ML model
            setImageAnalysisAnalyzer(
                androidx.core.content.ContextCompat.getMainExecutor(context),
                ObjectDetectorAnalyzer { boxes ->
                    // Update state, which triggers the Canvas to redraw
                    detectedObjects = boxes
                }
            )
        }
    }

    // --- 2. HUD ANIMATIONS ---
    val infiniteTransition = rememberInfiniteTransition(label = "hud")

    val gridOffset by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing)), label = "grid"
    )

    val scanLineY by infiniteTransition.animateFloat(
        initialValue = 0.1f, targetValue = 0.9f,
        animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing), RepeatMode.Reverse), label = "laser"
    )

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {

        // --- 3. LIVE CAMERA FEED (BACKGROUND) ---
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                PreviewView(ctx).apply {
                    controller = cameraController
                    // Keeps the aspect ratio clean
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                }
            }
        )

        // --- 4. THE 3D SCANNING CANVAS (OVERLAY) ---
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // A. Perspective 3D Grid
            val gridStartHeight = height * 0.65f
            for (i in 0..10) {
                val xFactor = (i / 10f)
                drawLine(
                    color = accentBlue.copy(0.25f),
                    start = Offset(width * 0.5f, gridStartHeight),
                    end = Offset(width * (xFactor * 2 - 0.5f), height),
                    strokeWidth = 1.5f
                )

                val yFactor = (i + gridOffset) / 10f
                val currentY = gridStartHeight + (height - gridStartHeight) * yFactor
                drawLine(
                    color = accentBlue.copy(0.2f * yFactor),
                    start = Offset(0f, currentY),
                    end = Offset(width, currentY),
                    strokeWidth = 2f
                )
                // Draw YOLOv8 Bounding Boxes
                detectedObjects.forEach { rect ->
                    drawRect(
                        color = Color.Green,
                        topLeft = Offset(rect.left.toFloat(), rect.top.toFloat()),
                        size = Size(rect.width().toFloat(), rect.height().toFloat()),
                        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 5f)
                    )
                }
            }

            // B. Central Scanning Brackets
            val boxW = width * 0.75f
            val boxH = height * 0.45f
            val left = (width - boxW) / 2
            val top = (height - boxH) / 2
            val bLen = 35.dp.toPx()

            // Top-Left
            drawLine(accentBlue, Offset(left, top), Offset(left + bLen, top), 5f)
            drawLine(accentBlue, Offset(left, top), Offset(left, top + bLen), 5f)
            // Top-Right
            drawLine(accentBlue, Offset(left + boxW, top), Offset(left + boxW - bLen, top), 5f)
            drawLine(accentBlue, Offset(left + boxW, top), Offset(left + boxW, top + bLen), 5f)
            // Bottom-Left
            drawLine(accentBlue, Offset(left, top + boxH), Offset(left + bLen, top + boxH), 5f)
            drawLine(accentBlue, Offset(left, top + boxH), Offset(left, top + boxH - bLen), 5f)
            // Bottom-Right
            drawLine(accentBlue, Offset(left + boxW, top + boxH), Offset(left + boxW - bLen, top + boxH), 5f)
            drawLine(accentBlue, Offset(left + boxW, top + boxH), Offset(left + boxW, top + boxH - bLen), 5f)

            // C. Laser Scan Line
            val laserY = top + (boxH * scanLineY)
            drawRect(
                brush = Brush.verticalGradient(
                    listOf(Color.Transparent, accentBlue.copy(0.4f), Color.Transparent)
                ),
                topLeft = Offset(left, laserY - 20f),
                size = Size(boxW, 40f)
            )
            drawLine(accentBlue, Offset(left, laserY), Offset(left + boxW, laserY), 3f)
        }

        // --- 5. UI CONTROLS ---
        Row(
            modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.background(Color.Black.copy(0.5f), CircleShape)
            ) {
                Icon(Icons.AutoMirrored.Rounded.ArrowBack, "Back", tint = Color.White)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("NEURAL_LINK_STREAM", color = accentBlue, fontSize = 10.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp)
                Text("60 FPS | TENSOR_ACTIVE", color = Color.White.copy(0.7f), fontSize = 8.sp, fontWeight = FontWeight.Bold)
            }

            IconButton(onClick = { }, modifier = Modifier.background(Color.Black.copy(0.5f), CircleShape)) {
                Icon(Icons.Rounded.Settings, null, tint = Color.White)
            }
        }

        // Bottom Stop Button
        Surface(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp)
                .height(64.dp)
                .width(200.dp),
            color = Color.Red.copy(0.2f),
            shape = RoundedCornerShape(20.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color.Red.copy(0.5f))
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Icon(Icons.Rounded.Stop, null, tint = Color.Red)
                Spacer(Modifier.width(8.dp))
                Text("TERMINATE", color = Color.Red, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
            }
        }
    }
}
class ObjectDetectorAnalyzer(
    private val onDetections: (List<Rect>) -> Unit
) : ImageAnalysis.Analyzer {

    // 1. Initialize your TFLite Interpreter here using your yolo.tflite asset
    // val tflite = Interpreter(loadModelFile())z

    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {

            // 2. Convert the ImageProxy to a Bitmap or TensorImage
            // 3. Feed the image into the TFLite Interpreter
            // 4. Parse the output arrays into bounding boxes (Rect)
            // 5. Filter out low-confidence detections

            val boundingBoxes = listOf<Rect>() // Replace with actual ML output

            // 6. Pass the boxes back to the UI
            onDetections(boundingBoxes)
        }

        // CRITICAL: You must close the image to receive the next frame
        imageProxy.close()
    }
}