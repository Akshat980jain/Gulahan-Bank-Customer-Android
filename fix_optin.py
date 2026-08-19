import re

with open("app/src/main/java/com/example/ui/screens/ScanScreen.kt", "r") as f:
    content = f.read()

content = content.replace("@Composable\nfun CameraPreview(onQrScanned: (String) -> Unit) {", "@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)\n@Composable\nfun CameraPreview(onQrScanned: (String) -> Unit) {")

with open("app/src/main/java/com/example/ui/screens/ScanScreen.kt", "w") as f:
    f.write(content)
