import re

with open("app/src/main/java/com/example/ui/screens/MoreScreen.kt", "r") as f:
    content = f.read()

# Add imports
if "import androidx.compose.runtime.*" not in content:
    content = content.replace("import androidx.compose.runtime.Composable", "import androidx.compose.runtime.*\nimport androidx.compose.foundation.shape.RoundedCornerShape")

# Add state
state_code = """    var showLogoutDialog by remember { mutableStateOf(false) }

    Scaffold("""

content = content.replace("    Scaffold(", state_code)

# Replace logout item
content = content.replace(
    'item { SettingsMenuItem(Icons.Outlined.Logout, "Logout", tint = MaterialTheme.colorScheme.error) { onNavigate("splash") } }',
    'item { SettingsMenuItem(Icons.Outlined.Logout, "Logout", tint = MaterialTheme.colorScheme.error) { showLogoutDialog = true } }'
)

# Add dialog
dialog_code = """
        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                icon = { Icon(Icons.Outlined.Logout, contentDescription = null, tint = MaterialTheme.colorScheme.error) },
                title = { Text("Sign Out", fontWeight = FontWeight.Bold) },
                text = { Text("Are you sure you want to securely sign out of your workstation? You will need to re-enter your credentials to access your account.") },
                confirmButton = {
                    Button(
                        onClick = {
                            showLogoutDialog = false
                            onNavigate("login")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Sign Out", color = MaterialTheme.colorScheme.onError)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutDialog = false }) {
                        Text("Cancel", color = MaterialTheme.colorScheme.onSurface)
                    }
                },
                shape = RoundedCornerShape(16.dp),
                containerColor = MaterialTheme.colorScheme.surface
            )
        }
    }
}
"""
content = content.replace("        }\n    }\n}", "        }\n" + dialog_code)

with open("app/src/main/java/com/example/ui/screens/MoreScreen.kt", "w") as f:
    f.write(content)
