import re

with open("app/src/main/java/com/example/ui/screens/TransactionSuccessScreen.kt", "r") as f:
    content = f.read()

new_imports = """import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
"""

if "import androidx.compose.ui.hapticfeedback.HapticFeedbackType" not in content:
    content = content.replace("import androidx.compose.ui.unit.sp", "import androidx.compose.ui.unit.sp\n" + new_imports)


content = content.replace("val progress by animateLottieCompositionAsState(", """
    val haptic = LocalHapticFeedback.current
    LaunchedEffect(Unit) {
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
    }
    
    val progress by animateLottieCompositionAsState(""")

content = content.replace("onClick = onNavigateHome,", """onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                onNavigateHome()
            },""")

with open("app/src/main/java/com/example/ui/screens/TransactionSuccessScreen.kt", "w") as f:
    f.write(content)
