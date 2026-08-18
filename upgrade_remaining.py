import glob

files = glob.glob("app/src/main/java/com/example/ui/screens/*.kt")

for file_path in files:
    with open(file_path, "r") as f:
        content = f.read()

    # Padding and Shapes
    content = content.replace("padding(16.dp)", "padding(24.dp)")
    content = content.replace("RoundedCornerShape(16.dp)", "MaterialTheme.shapes.large")
    content = content.replace("RoundedCornerShape(12.dp)", "MaterialTheme.shapes.medium")

    # Card Elegance
    content = content.replace("elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)", "elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)")
    content = content.replace("border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))", "border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))")

    with open(file_path, "w") as f:
        f.write(content)
