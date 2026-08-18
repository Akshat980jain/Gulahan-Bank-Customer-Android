import os

color_kt = """package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Light Theme Colors - Premium Fintech
val BackgroundLight = Color(0xFFF7F8FA) // Very subtle off-white/gray
val SurfaceLight = Color(0xFFFFFFFF)
val SurfaceVariantLight = Color(0xFFF1F3F9)
val PrimaryBlue = Color(0xFF2563EB) // Electric Indigo/Blue
val PrimaryVariant = Color(0xFF1D4ED8)
val SecondaryGold = Color(0xFFF59E0B) // Premium gold accent
val TextPrimary = Color(0xFF111827) // Almost black
val TextSecondary = Color(0xFF6B7280) // Refined gray
val BorderLight = Color(0xFFE5E7EB)

// Dark Theme Colors - True Premium Dark
val BackgroundDark = Color(0xFF0F1115) // Deep space black
val SurfaceDark = Color(0xFF161920) // Slightly elevated dark
val SurfaceVariantDark = Color(0xFF20242D)
val PrimaryBlueDark = Color(0xFF3B82F6)
val TextPrimaryDark = Color(0xFFF9FAFB)
val TextSecondaryDark = Color(0xFF9CA3AF)
val BorderDark = Color(0xFF2A2E37)

// Semantic Colors
val SuccessGreen = Color(0xFF10B981)
val ErrorRed = Color(0xFFEF4444)
val WarningYellow = Color(0xFFF59E0B)
val InfoBlue = Color(0xFF3B82F6)

// Gradients/Accents (for cards)
val CardGradientStart = Color(0xFF1E1E24)
val CardGradientEnd = Color(0xFF141519)
val PremiumGoldStart = Color(0xFFFBBF24)
val PremiumGoldEnd = Color(0xFFD97706)
"""

theme_kt = """package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    primaryContainer = SurfaceVariantLight,
    onPrimaryContainer = PrimaryBlue,
    secondary = SecondaryGold,
    background = BackgroundLight,
    surface = SurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,
    outline = BorderLight,
    error = ErrorRed
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlueDark,
    onPrimary = Color.White,
    primaryContainer = SurfaceVariantDark,
    onPrimaryContainer = PrimaryBlueDark,
    secondary = SecondaryGold,
    background = BackgroundDark,
    surface = SurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark,
    onSurfaceVariant = TextSecondaryDark,
    outline = BorderDark,
    error = ErrorRed
)

@Composable
fun GulshanEBankTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Disable dynamic color to maintain the premium curated look
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
"""

type_kt = """package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Refined typography scale for a premium feel
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = (-0.5).sp
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.5).sp
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.15.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
"""

shape_kt = """package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Standardized premium shapes
val Shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)
"""

def write_file(path, content):
    with open(path, "w") as f:
        f.write(content)

write_file("app/src/main/java/com/example/ui/theme/Color.kt", color_kt)
write_file("app/src/main/java/com/example/ui/theme/Theme.kt", theme_kt)
write_file("app/src/main/java/com/example/ui/theme/Type.kt", type_kt)
write_file("app/src/main/java/com/example/ui/theme/Shape.kt", shape_kt)
print("Design system updated.")
