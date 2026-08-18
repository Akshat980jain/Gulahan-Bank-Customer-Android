import re

with open("app/src/main/java/com/example/ui/screens/AuthScreens.kt", "r") as f:
    content = f.read()

# Add imports
if "import com.example.R" not in content:
    content = content.replace("import androidx.compose.ui.unit.sp", "import androidx.compose.ui.unit.sp\nimport androidx.compose.foundation.Image\nimport androidx.compose.ui.res.painterResource\nimport com.example.R")

# Replace Icon with Image in LoginScreen
content = content.replace("""                Icon(
                    imageVector = Icons.Default.AccountBalance,
                    contentDescription = "Logo",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )""", """                Image(
                    painter = painterResource(id = R.drawable.logo_recreated_1786856553161),
                    contentDescription = "Logo",
                    modifier = Modifier.size(36.dp).clip(RoundedCornerShape(8.dp))
                )""")

with open("app/src/main/java/com/example/ui/screens/AuthScreens.kt", "w") as f:
    f.write(content)
