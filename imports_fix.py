import re
import glob

files = glob.glob("app/src/main/java/com/example/**/*.kt", recursive=True)
for f_path in files:
    with open(f_path, "r") as f:
        content = f.read()

    # Check for com.example.ui.screens.com.example.ui.screens...
    content = content.replace("package com.example.ui.screens.TransactionSuccessScreen", "package com.example.ui.screens")
    content = content.replace("com.example.ui.screens.com.example.ui.screens.TransactionSuccessScreen", "com.example.ui.screens.TransactionSuccessScreen")
    
    # Check for bad MainActivity replacement
    if "MainActivity.kt" in f_path:
        content = content.replace("com.example.ui.screens.TransactionSuccessScreen", "TransactionSuccessScreen")

    # If AccountScreens.kt has issues with MaterialTheme... it's probably com.example.ui.theme.MaterialTheme getting imported by `import com.example.ui.theme.*` ?
    # Wait, Theme.kt defines GulshanEBankTheme, not MaterialTheme. BUT in my super_fix.py, did I put MaterialTheme in Color.kt? No.
    # Ah, `import androidx.compose.material3.*` should cover it. Why is it unresolved?
    # Because there might be a typo, like `MaterialTheme.typography.titleMedium` vs `com.example.ui.theme.MaterialTheme...`
    # Let's explicitly replace `MaterialTheme.` with `androidx.compose.material3.MaterialTheme.` where it's unresolved?
    # No, let's just make sure there are no syntax errors.

    with open(f_path, "w") as f:
        f.write(content)
