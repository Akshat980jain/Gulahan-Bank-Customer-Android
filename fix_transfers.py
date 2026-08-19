import re

with open("app/src/main/java/com/example/ui/screens/TransfersScreen.kt", "r") as f:
    content = f.read()

content = content.replace('TransferMethodButton(Icons.Outlined.AccountBalance, "To Bank\\nAccount") { onNavigate("transaction_success") }', 'TransferMethodButton(Icons.Outlined.AccountBalance, "To Bank\\nAccount") { onNavigate("bank_transfer") }')

with open("app/src/main/java/com/example/ui/screens/TransfersScreen.kt", "w") as f:
    f.write(content)
