package com.example.ui.screens

import android.Manifest
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FlashlightOn
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.ui.components.MainBottomNavigation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScanScreen(currentScreen: String, onNavigate: (String) -> Unit) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect(Unit) {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    Scaffold(
        containerColor = Color(0xFF111111),
        bottomBar = { MainBottomNavigation(currentScreen, onNavigate) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Scan any QR to Pay",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(48.dp))
                
                // Scanner reticle
                Box(
                    modifier = Modifier
                        .size(260.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .border(2.dp, Color.White.copy(alpha = 0.5f), RoundedCornerShape(24.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (cameraPermissionState.status.isGranted) {
                        CameraPreview()
                    } else {
                        Icon(
                            Icons.Outlined.QrCodeScanner,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.2f),
                            modifier = Modifier.size(120.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(48.dp))
                
                Row(horizontalArrangement = Arrangement.spacedBy(48.dp)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(
                            onClick = {},
                            modifier = Modifier.background(Color.White.copy(alpha = 0.1f), MaterialTheme.shapes.large).size(56.dp)
                        ) {
                            Icon(Icons.Outlined.PhotoLibrary, contentDescription = "Gallery", tint = Color.White, modifier = Modifier.size(28.dp))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Gallery", color = Color.White, fontSize = 12.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(
                            onClick = {},
                            modifier = Modifier.background(Color.White.copy(alpha = 0.1f), MaterialTheme.shapes.large).size(56.dp)
                        ) {
                            Icon(Icons.Outlined.FlashlightOn, contentDescription = "Torch", tint = Color.White, modifier = Modifier.size(28.dp))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Torch", color = Color.White, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun CameraPreview() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    
    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
            
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.surfaceProvider = previewView.surfaceProvider
                }
                
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview
                    )
                } catch (exc: Exception) {
                    // Handle exceptions
                }
            }, ContextCompat.getMainExecutor(ctx))
            
            previewView
        },
        modifier = Modifier.fillMaxSize()
    )
}
