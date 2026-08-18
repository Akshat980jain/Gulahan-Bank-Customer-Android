import re
import glob

# 1. Fix Color.kt
color_kt = """package com.example.ui.theme

import androidx.compose.ui.graphics.Color

val BackgroundLight = Color(0xFFF7F8FA)
val SurfaceLight = Color(0xFFFFFFFF)
val SurfaceVariantLight = Color(0xFFF1F3F9)
val PrimaryBlue = Color(0xFF2563EB)
val PrimaryVariant = Color(0xFF1D4ED8)
val SecondaryGold = Color(0xFFF59E0B)
val TextPrimary = Color(0xFF111827)
val TextSecondary = Color(0xFF6B7280)
val BorderLight = Color(0xFFE5E7EB)

val BackgroundDark = Color(0xFF0F1115)
val SurfaceDark = Color(0xFF161920)
val SurfaceVariantDark = Color(0xFF20242D)
val PrimaryBlueDark = Color(0xFF3B82F6)
val TextPrimaryDark = Color(0xFFF9FAFB)
val TextSecondaryDark = Color(0xFF9CA3AF)
val BorderDark = Color(0xFF2A2E37)

val SuccessGreen = Color(0xFF10B981)
val ErrorRed = Color(0xFFEF4444)
val WarningYellow = Color(0xFFF59E0B)
val InfoBlue = Color(0xFF3B82F6)

val CardGradientStart = Color(0xFF1E1E24)
val CardGradientEnd = Color(0xFF141519)
val PremiumGoldStart = Color(0xFFFBBF24)
val PremiumGoldEnd = Color(0xFFD97706)
"""
with open("app/src/main/java/com/example/ui/theme/Color.kt", "w") as f:
    f.write(color_kt)

# 2. Fix misplaced imports in all files (e.g. AccountScreens.kt)
files = glob.glob("app/src/main/java/com/example/**/*.kt", recursive=True)
for file_path in files:
    with open(file_path, "r") as f:
        content = f.read()
    
    # Fix import at line 1
    if content.startswith("import androidx.compose.material3.MaterialTheme\npackage"):
        content = content.replace("import androidx.compose.material3.MaterialTheme\npackage", "package")
        content = content.replace("package com.example.ui.screens", "package com.example.ui.screens\n\nimport androidx.compose.material3.MaterialTheme")
    
    # Also for main activity or other places
    
    # Fix Text duplicates like "color = ..., style = ..." if they conflict, actually the compiler error was:
    # "None of the following candidates is applicable:"
    # This usually means multiple conflicting params (e.g., `fontSize` and `style` mixed, or `fontWeight` and `style`).
    # Wait, `Text(..., color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)` is VALID.
    # What caused the error in HomeScreen?
    # Let's inspect HomeScreen errors: 
    # e: HomeScreen.kt:119 "None of the following candidates is applicable"
    # The script replaced `fontSize = 13.sp` with `style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant`
    # Let's strip away all those custom text styles and just rely on Compose
    
    with open(file_path, "w") as f:
        f.write(content)
