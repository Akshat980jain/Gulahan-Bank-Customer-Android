import re

with open("app/src/main/java/com/example/ui/screens/TransactionsScreen.kt", "r") as f:
    content = f.read()

# Padding and Shapes
content = content.replace("padding(16.dp)", "padding(24.dp)")
content = content.replace("RoundedCornerShape(16.dp)", "MaterialTheme.shapes.large")
content = content.replace("RoundedCornerShape(12.dp)", "MaterialTheme.shapes.medium")
content = content.replace("size(48.dp)", "size(56.dp)")

# Typography
content = content.replace("fontSize = 16.sp, fontWeight = FontWeight.SemiBold", "style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold")
content = content.replace("fontSize = 15.sp, fontWeight = FontWeight.Medium", "style = MaterialTheme.typography.titleMedium")
content = content.replace("fontSize = 13.sp", "style = MaterialTheme.typography.bodyMedium")
content = content.replace("fontSize = 14.sp", "style = MaterialTheme.typography.bodyMedium")
content = content.replace("fontSize = 12.sp, fontWeight = FontWeight.Medium", "style = MaterialTheme.typography.labelMedium")

# Card Elegance
content = content.replace("elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)", "elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)")
content = content.replace("border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))", "border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))")

with open("app/src/main/java/com/example/ui/screens/TransactionsScreen.kt", "w") as f:
    f.write(content)

