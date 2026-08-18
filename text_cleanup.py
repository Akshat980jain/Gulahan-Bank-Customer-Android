import re
import glob

files = glob.glob("app/src/main/java/com/example/**/*.kt", recursive=True)
for f_path in files:
    with open(f_path, "r") as f:
        content = f.read()

    # Find Text(...) calls and remove duplicate color parameters
    # This regex is a bit complex, let's just do simple replacements
    content = content.replace("color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant", "color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium")
    content = content.replace("style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, color = MaterialTheme.colorScheme.onSurfaceVariant", "style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant")
    
    # Check for duplicate fontWeight
    content = content.replace("fontWeight = FontWeight.Medium, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium", "style = MaterialTheme.typography.titleMedium")
    
    # Let's fix MainActivity references
    content = content.replace("TransactionSuccessScreen", "com.example.ui.screens.TransactionSuccessScreen")
    
    with open(f_path, "w") as f:
        f.write(content)
