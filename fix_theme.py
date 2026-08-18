import os
import glob

replacements = {
    "BackgroundLight": "MaterialTheme.colorScheme.background",
    "SurfaceLight": "MaterialTheme.colorScheme.surface",
    "TextPrimary": "MaterialTheme.colorScheme.onSurface",
    "TextSecondary": "MaterialTheme.colorScheme.onSurfaceVariant",
    "PrimaryBlue": "MaterialTheme.colorScheme.primary",
    "BorderLight": "MaterialTheme.colorScheme.outline",
    "ErrorRed": "MaterialTheme.colorScheme.error",
    "DividerColor": "MaterialTheme.colorScheme.outline"
}

files = glob.glob("app/src/main/java/com/example/**/*.kt", recursive=True)
for f in files:
    if "Theme.kt" in f or "Color.kt" in f:
        continue
    with open(f, "r") as file:
        content = file.read()
        
    for k, v in replacements.items():
        content = content.replace(k, v)
        
    with open(f, "w") as file:
        file.write(content)
