import re

with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    content = f.read()

if 'composable("bank_transfer")' not in content:
    content = content.replace('composable("add_money") { AddMoneyScreen', 'composable("bank_transfer") { BankTransferScreen(onNavigateBack = { navController.popBackStack() }, onNavigateSuccess = { navController.navigate("transaction_success") }) }\n                            composable("add_money") { AddMoneyScreen')

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(content)
