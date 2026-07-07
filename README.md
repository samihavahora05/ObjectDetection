<div align="center">

<img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white"/>
<img src="https://img.shields.io/badge/Language-Kotlin%20100%25-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white"/>
<img src="https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white"/>
<img src="https://img.shields.io/badge/Camera-CameraX-FF6F00?style=for-the-badge"/>
<img src="https://img.shields.io/badge/Model-YOLOv8%20%2F%20TFLite-8E24AA?style=for-the-badge"/>
<img src="https://img.shields.io/badge/Status-In%20Development-yellow?style=for-the-badge"/>

<br/><br/>

# 🎯 Object Detection System

### *A real-time, on-device object detection app with a sci-fi neural-scan interface.*

A native Android app built entirely in Kotlin + Jetpack Compose, designed around a live camera feed with an animated HUD (heads-up display) overlay — scanning brackets, a perspective grid, a laser scan line, and live bounding boxes — built to visualize on-device object detection powered by **YOLOv8** through **TensorFlow Lite**.

<br/>

[✨ Features](#-features) • [📸 Screens](#-screens) • [🛠️ Tech Stack](#%EF%B8%8F-tech-stack) • [🏗️ Architecture](#%EF%B8%8F-architecture) • [🚀 Getting Started](#-getting-started) • [🔮 Roadmap](#-roadmap)

</div>

---

## ✨ Features

| Feature | Description |
|---|---|
| 📷 **Live Camera Feed** | Full-screen CameraX preview as the base layer for real-time analysis |
| 🎯 **Detection HUD Overlay** | Animated perspective grid, corner scan brackets, and a sweeping laser line drawn with Compose `Canvas` |
| 🟩 **Live Bounding Boxes** | Detected objects are outlined in real time directly over the camera feed |
| 📊 **Stats Dashboard** | Inference speed, average confidence, model size, and session counters |
| 🕓 **Detection Logs** | Searchable history of past detections with timestamps and confidence scores |
| ⚙️ **Configurable Engine** | Toggle high-precision mode, offline-only processing, vibration/battery preferences |
| ℹ️ **About the Model** | In-app screen explaining the detection model and how it works |
| 🌑 **Dark, HUD-Style UI** | Consistent deep-space theme (`#020408` background, `#007BFF` neon-blue accent) across every screen |

---

## 📸 Screens

<div align="center">
<table>
  <tr>
    <td align="center"><b>🏠 Home</b></td>
    <td align="center"><b>🎯 Detection HUD</b></td>
    <td align="center"><b>📊 Stats</b></td>
  </tr>
  <tr>
    <td><img src="screenshots/home_mockup.png" width="220"/></td>
    <td><img src="screenshots/detection_mockup.png" width="220"/></td>
    <td><img src="screenshots/stats_mockup.png" width="220"/></td>
  </tr>
</table>



---

## 🛠️ Tech Stack

```
├── Language        →  Kotlin (100%)
├── UI Framework    →  Jetpack Compose (Material 3)
├── Navigation      →  Compose Navigation (NavGraph, single-activity)
├── Camera          →  CameraX (core, camera2, lifecycle, view)
├── ML Pipeline     →  TensorFlow Lite + YOLOv8 (on-device, planned/in-progress)
└── Build System    →  Gradle (Kotlin DSL)
```

### Project Structure

```
app/src/main/java/com/example/objectdetection_system/
├── MainActivity.kt              # Single-activity entry point, hosts the NavGraph
├── NavGraph.kt                  # Navigation graph wiring all screens together
├── ui/theme/                    # Color.kt, Type.kt, Theme.kt — app-wide Material theme
└── ui/screens/
    ├── SplashScreen.kt          # Animated boot/loading sequence
    ├── HomeScreen.kt            # Dashboard, quick stats, recent activity, start button
    ├── DetectionScreen.kt       # Live camera feed + HUD overlay + bounding boxes
    ├── StatsScreen.kt           # Inference speed, confidence, model size, session count
    ├── LogsScreen.kt            # Searchable detection history
    ├── SettingsScreen.kt        # Precision mode, offline processing, vibration, battery
    └── AboutModelScreen.kt      # Explains the underlying detection model
```

---

## 🏗️ Architecture

```
CameraX PreviewView (live feed)
        ↓
ImageAnalysis.Analyzer (ObjectDetectorAnalyzer)
        ↓
TensorFlow Lite Interpreter  →  YOLOv8 model (.tflite)
        ↓
Bounding Box list (List<Rect>)
        ↓
Compose State (mutableStateOf)
        ↓
Canvas overlay — draws HUD grid, scan brackets, and live boxes
```

The `DetectionScreen` already wires up the **CameraX → Analyzer → Compose Canvas** pipeline end-to-end. The `ObjectDetectorAnalyzer` class is scaffolded with the exact steps needed to plug in a `.tflite` model — converting each camera frame, running inference, parsing YOLOv8 output into bounding boxes, and filtering by confidence — ready for the model integration step.

---

## 🚀 Getting Started

### Prerequisites

- Android Studio (Hedgehog or later)
- JDK 11+
- An Android device or emulator with camera support

### Installation

```bash
git clone https://github.com/samihavahora05/ObjectDetection.git
cd ObjectDetection
```

Open in Android Studio → Sync Gradle → Run on a device/emulator with camera access.

### Adding the detection model

To enable live inference:

1. Drop a YOLOv8 `.tflite` model file into `app/src/main/assets/`
2. Add the TensorFlow Lite dependencies to `app/build.gradle.kts`:
   ```kotlin
   implementation("org.tensorflow:tensorflow-lite:2.14.0")
   implementation("org.tensorflow:tensorflow-lite-support:0.4.4")
   ```
3. Complete the `ObjectDetectorAnalyzer.analyze()` function in `DetectionScreen.kt` — it's scaffolded with numbered steps for loading the interpreter, running inference, and parsing bounding boxes.

---

## 🔮 Roadmap

- 🧠 Wire up the TensorFlow Lite interpreter and a trained YOLOv8 `.tflite` model
- 🎚️ Live confidence-threshold slider in Settings
- 🏷️ Class labels rendered alongside each bounding box
- 💾 Persist detection logs locally (Room database)
- 📤 Export detection history as CSV/JSON


<div align="center">

⭐ If you found this project interesting, give it a star!

Built with Kotlin, Jetpack Compose, and CameraX.

</div>
