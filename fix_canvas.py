file_path = "app/src/main/java/com/example/ui/screens/HomeScreen.kt"
with open(file_path, "r") as f:
    content = f.read()

content = content.replace("androidx.compose.foundation.Canvas(modifier = Modifier.size(160.dp)) {", "val primaryColor = MaterialTheme.colorScheme.primary\n                    androidx.compose.foundation.Canvas(modifier = Modifier.size(160.dp)) {")
content = content.replace("val colors = listOf(MaterialTheme.colorScheme.primary, Color(0xFF6366F1)", "val colors = listOf(primaryColor, Color(0xFF6366F1)")

with open(file_path, "w") as f:
    f.write(content)
