import re

with open("app/src/main/java/com/example/ui/screens/CardsScreen.kt", "r") as f:
    content = f.read()

# Make corners rounder and paddings more spacious
content = content.replace("RoundedCornerShape(16.dp)", "MaterialTheme.shapes.large")
content = content.replace("RoundedCornerShape(12.dp)", "MaterialTheme.shapes.medium")
content = content.replace("padding(20.dp)", "padding(24.dp)")
content = content.replace("padding(16.dp)", "padding(24.dp)")

# Upgrade fonts
content = content.replace("fontSize = 24.sp, fontWeight = FontWeight.Bold", "style = MaterialTheme.typography.displaySmall")
content = content.replace("fontSize = 22.sp, fontWeight = FontWeight.Medium", "style = MaterialTheme.typography.headlineMedium, letterSpacing = 2.sp")
content = content.replace("fontSize = 16.sp", "style = MaterialTheme.typography.titleMedium")
content = content.replace("fontSize = 15.sp, fontWeight = FontWeight.Medium", "style = MaterialTheme.typography.titleMedium")
content = content.replace("fontSize = 14.sp", "style = MaterialTheme.typography.bodyMedium")
content = content.replace("fontSize = 13.sp", "style = MaterialTheme.typography.labelLarge")
content = content.replace("fontSize = 12.sp", "style = MaterialTheme.typography.labelMedium")

# Better card proportions
content = content.replace("height(200.dp)", "height(220.dp)")

# Card gradient
content = content.replace(
    "Brush.linearGradient(colors = listOf(Color(0xFF1E1E24), Color(0xFF141519)))",
    "Brush.linearGradient(colors = listOf(com.example.ui.theme.CardGradientStart, com.example.ui.theme.CardGradientEnd))"
)

with open("app/src/main/java/com/example/ui/screens/CardsScreen.kt", "w") as f:
    f.write(content)

