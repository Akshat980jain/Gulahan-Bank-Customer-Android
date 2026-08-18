import re

with open("app/src/main/java/com/example/ui/screens/TransfersScreen.kt", "r") as f:
    content = f.read()

# Update TransferMethodButton signature
content = content.replace("fun TransferMethodButton(icon: ImageVector, label: String) {", "fun TransferMethodButton(icon: ImageVector, label: String, onClick: () -> Unit = {}) {")

content = content.replace("""        Box(
            modifier = Modifier
                .size(56.dp)
                .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.large)
                .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), MaterialTheme.shapes.large),""", """        val haptic = LocalHapticFeedback.current
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.large)
                .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), MaterialTheme.shapes.large)
                .clickable {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onClick()
                },""")

# And add the LocalHapticFeedback import if not present
if "import androidx.compose.ui.platform.LocalHapticFeedback" not in content:
    content = content.replace("import androidx.compose.ui.graphics.vector.ImageVector", "import androidx.compose.ui.graphics.vector.ImageVector\nimport androidx.compose.ui.platform.LocalHapticFeedback\nimport androidx.compose.ui.hapticfeedback.HapticFeedbackType")

# Update usages in TransfersScreen
content = content.replace('TransferMethodButton(Icons.Outlined.AccountBalance, "To Bank\\nAccount")', 'TransferMethodButton(Icons.Outlined.AccountBalance, "To Bank\\nAccount") { onNavigate("transaction_success") }')
content = content.replace('TransferMethodButton(Icons.Outlined.AlternateEmail, "To UPI\\nID")', 'TransferMethodButton(Icons.Outlined.AlternateEmail, "To UPI\\nID") { onNavigate("transaction_success") }')
content = content.replace('TransferMethodButton(Icons.Outlined.Contacts, "To\\nContact")', 'TransferMethodButton(Icons.Outlined.Contacts, "To\\nContact") { onNavigate("transaction_success") }')
content = content.replace('TransferMethodButton(Icons.Outlined.SyncAlt, "Self\\nTransfer")', 'TransferMethodButton(Icons.Outlined.SyncAlt, "Self\\nTransfer") { onNavigate("transaction_success") }')

# Make Recent Payees clickable
content = content.replace("""                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {""", """                            val haptic = LocalHapticFeedback.current
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                    .clickable {
                                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                        onNavigate("transaction_success")
                                    },
                                contentAlignment = Alignment.Center
                            ) {""")

with open("app/src/main/java/com/example/ui/screens/TransfersScreen.kt", "w") as f:
    f.write(content)
