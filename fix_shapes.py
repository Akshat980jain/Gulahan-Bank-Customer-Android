import glob

files = glob.glob("app/src/main/java/com/example/**/*.kt", recursive=True)
for f_path in files:
    with open(f_path, "r") as f:
        content = f.read()

    # Fix bad shape references
    content = content.replace("androidx.compose.foundation.shape.MaterialTheme", "MaterialTheme")
    
    with open(f_path, "w") as f:
        f.write(content)
