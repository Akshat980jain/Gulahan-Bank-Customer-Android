import re
import glob

# Fix Theme.kt
with open("app/src/main/java/com/example/ui/theme/Theme.kt", "r") as f:
    theme_kt = f.read()
if "import androidx.compose.ui.graphics.Color" not in theme_kt:
    theme_kt = theme_kt.replace("import androidx.compose.runtime.Composable", "import androidx.compose.runtime.Composable\nimport androidx.compose.ui.graphics.Color")
with open("app/src/main/java/com/example/ui/theme/Theme.kt", "w") as f:
    f.write(theme_kt)

# Add missing colors to Color.kt
with open("app/src/main/java/com/example/ui/theme/Color.kt", "a") as f:
    f.write("\nval BadgeBackground = Color(0xFFD6E3FF)\nval BadgeText = Color(0xFF001B3E)\n")

# Replace BadgeBackground with MaterialTheme.colorScheme.primaryContainer across the app for a more premium look
files = glob.glob("app/src/main/java/com/example/**/*.kt", recursive=True)
for file_path in files:
    with open(file_path, "r") as f:
        content = f.read()
    
    content = content.replace("BadgeBackground", "MaterialTheme.colorScheme.primaryContainer")
    content = content.replace("BadgeText", "MaterialTheme.colorScheme.onPrimaryContainer")
    
    # Fix the Text candidates issue - there might be a duplicate parameter or trailing comma problem
    # Let's see what the python script did: "style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant"
    # If the original was `Text("xyz", color = ..., fontSize = 13.sp)` and it was replaced with `style = ..., color = ...` wait, color is defined twice!
    # Ah! The original was `color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp`.
    # And I replaced `fontSize = 13.sp` with `style = ..., color = MaterialTheme.colorScheme.onSurfaceVariant`
    # So now it says `color = MaterialTheme.colorScheme.onSurfaceVariant, style = ..., color = ...` which is duplicate named parameter!
    content = content.replace(", color = MaterialTheme.colorScheme.onSurfaceVariant, color = MaterialTheme.colorScheme.onSurfaceVariant", ", color = MaterialTheme.colorScheme.onSurfaceVariant")
    
    # Fix any occurrences of duplicate color in Text
    # Let's just fix the bad replacements directly
    content = content.replace("style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant", "style = MaterialTheme.typography.bodyMedium")
    
    # Also I replaced `fontSize = 24.sp, fontWeight = FontWeight.Bold` which might have left duplicates if color was there. 
    # Let's fix the specific error files
    
    with open(file_path, "w") as f:
        f.write(content)
