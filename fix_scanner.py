import re

with open("app/src/main/java/com/example/ui/screens/ScanScreen.kt", "r") as f:
    content = f.read()

# Replace CameraPreview definition
new_camera_preview = """@Composable
fun CameraPreview(onQrScanned: (String) -> Unit) {
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
                
                val imageAnalysis = androidx.camera.core.ImageAnalysis.Builder()
                    .setBackpressureStrategy(androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                
                val scanner = com.google.mlkit.vision.barcode.BarcodeScanning.getClient(
                    com.google.mlkit.vision.barcode.BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(com.google.mlkit.vision.barcode.common.Barcode.FORMAT_QR_CODE)
                        .build()
                )
                
                imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(ctx)) { imageProxy ->
                    val mediaImage = imageProxy.image
                    if (mediaImage != null) {
                        val image = com.google.mlkit.vision.common.InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                        scanner.process(image)
                            .addOnSuccessListener { barcodes ->
                                if (barcodes.isNotEmpty()) {
                                    val rawValue = barcodes.first().rawValue
                                    if (rawValue != null) {
                                        onQrScanned(rawValue)
                                    }
                                }
                            }
                            .addOnCompleteListener {
                                imageProxy.close()
                            }
                    } else {
                        imageProxy.close()
                    }
                }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                } catch (exc: Exception) {
                    // Handle exceptions
                }
            }, ContextCompat.getMainExecutor(ctx))
            
            previewView
        },
        modifier = Modifier.fillMaxSize()
    )
}"""

content = re.sub(r"@Composable\nfun CameraPreview\(\) \{.*", new_camera_preview, content, flags=re.DOTALL)

# Update CameraPreview() call in ScanScreen
content = content.replace("CameraPreview()", """
                        var hasNavigated by remember { mutableStateOf(false) }
                        CameraPreview(onQrScanned = { qrCode -> 
                            if (!hasNavigated) {
                                hasNavigated = true
                                onNavigate("transaction_success")
                            }
                        })
""")

with open("app/src/main/java/com/example/ui/screens/ScanScreen.kt", "w") as f:
    f.write(content)
