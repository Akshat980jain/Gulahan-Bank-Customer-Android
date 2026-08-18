import re

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    content = f.read()

# Make corners rounder and paddings more spacious
content = content.replace("RoundedCornerShape(16.dp)", "MaterialTheme.shapes.large")
content = content.replace("RoundedCornerShape(12.dp)", "MaterialTheme.shapes.medium")
content = content.replace("padding(20.dp)", "padding(24.dp)")
content = content.replace("padding(16.dp)", "padding(24.dp)")
content = content.replace("height(16.dp)", "height(24.dp)")
content = content.replace("height(32.dp)", "height(40.dp)")

# Upgrade fonts
content = content.replace("fontSize = 26.sp, fontWeight = FontWeight.Bold", "style = MaterialTheme.typography.displaySmall")
content = content.replace("fontSize = 18.sp, fontWeight = FontWeight.Bold", "style = MaterialTheme.typography.headlineSmall")
content = content.replace("fontSize = 15.sp, fontWeight = FontWeight.Bold", "style = MaterialTheme.typography.titleMedium")
content = content.replace("fontSize = 15.sp, fontWeight = FontWeight.Medium", "style = MaterialTheme.typography.titleMedium")
content = content.replace("fontSize = 14.sp", "style = MaterialTheme.typography.bodyMedium")
content = content.replace("fontSize = 13.sp", "style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant")
content = content.replace("fontSize = 12.sp", "style = MaterialTheme.typography.labelMedium")

# Card styling
content = content.replace("elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)", "elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)")
content = content.replace("border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))", "border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))")

# Action Buttons styling
content = content.replace("size(56.dp)", "size(64.dp)")
content = content.replace("size(24.dp)", "size(28.dp)") # Slightly larger icons

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
    f.write(content)

