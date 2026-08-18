import re

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    content = f.read()

# 1. Update RecentTransactionsSection to use LazyColumn
old_recent_transactions = """        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column {
                TransactionItem(Icons.Outlined.PlayArrow, "Netflix", "Entertainment · Today", -649.0)
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                TransactionItem(Icons.Outlined.WorkOutline, "Salary", "Income · Aug 13", 45000.0, isPositive = true)
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                TransactionItem(Icons.Outlined.Person, "UPI Transfer to Vinay", "Transfer · Aug 13", -2500.0)
            }
        }"""

new_recent_transactions = """        Card(
            modifier = Modifier.fillMaxWidth().height(260.dp), // Fixed height for internal scrolling
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item { TransactionItem(Icons.Outlined.PlayArrow, "Netflix", "Entertainment · Today", -649.0) }
                item { HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)) }
                item { TransactionItem(Icons.Outlined.WorkOutline, "Salary", "Income · Aug 13", 45000.0, isPositive = true) }
                item { HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)) }
                item { TransactionItem(Icons.Outlined.Person, "UPI Transfer to Vinay", "Transfer · Aug 13", -2500.0) }
                item { HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)) }
                item { TransactionItem(Icons.Outlined.ShoppingCart, "Amazon", "Shopping · Aug 12", -1200.0) }
                item { HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)) }
                item { TransactionItem(Icons.Outlined.Restaurant, "Zomato", "Food · Aug 11", -450.0) }
            }
        }"""

content = content.replace(old_recent_transactions, new_recent_transactions)

# 2. Visually Engaging Account Balance card
old_net_worth = """    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {"""

new_net_worth = """    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = androidx.compose.ui.graphics.Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF0F172A), // Deep Navy
                        MaterialTheme.colorScheme.primary, // Primary Blue
                        Color(0xFF3B82F6) // Bright Blue
                    )
                )
            )
        ) {"""

content = content.replace(old_net_worth, new_net_worth)

# Since we added a Box, we need to add a closing brace. The easiest way is to replace the closing brace of the Column inside NetWorthCard
old_net_worth_closing = """            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                NetWorthBreakdown("Cash", 154890.0, isBalanceVisible)
                NetWorthBreakdown("Investments", 100000.0, isBalanceVisible)
                NetWorthBreakdown("Credit", 0.0, isBalanceVisible)
            }
        }
    }
}"""

new_net_worth_closing = """            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                NetWorthBreakdown("Cash", 154890.0, isBalanceVisible)
                NetWorthBreakdown("Investments", 100000.0, isBalanceVisible)
                NetWorthBreakdown("Credit", 0.0, isBalanceVisible)
            }
        }
        // Add subtle overlay circles for engaging visuals
        androidx.compose.foundation.Canvas(modifier = Modifier.matchParentSize()) {
            drawCircle(
                color = Color.White.copy(alpha = 0.05f),
                radius = size.width / 2.5f,
                center = androidx.compose.ui.geometry.Offset(size.width * 1.1f, size.height * -0.2f)
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.03f),
                radius = size.width / 3f,
                center = androidx.compose.ui.geometry.Offset(size.width * 0.9f, size.height * 1.1f)
            )
        }
        }
    }
}"""

content = content.replace(old_net_worth_closing, new_net_worth_closing)

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
    f.write(content)
