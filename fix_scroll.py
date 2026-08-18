import re

with open("app/src/main/java/com/example/ui/screens/AuthScreens.kt", "r") as f:
    content = f.read()

# 1. Remove verticalScroll from the outer columns
content = content.replace("            .background(DarkNavy)\n            .verticalScroll(rememberScrollState())", "            .background(DarkNavy)")

# 2. Add verticalScroll to the inner columns inside the Surface
content = content.replace("                modifier = Modifier\n                    .fillMaxSize()\n                    .padding(24.dp)", "                modifier = Modifier\n                    .fillMaxSize()\n                    .verticalScroll(rememberScrollState())\n                    .padding(24.dp)")

# 3. Fix the squished QR button text column
content = content.replace("                    Column {\n                        Text(\"Log in to NetBanking\",", "                    Column(modifier = Modifier.weight(1f).padding(end = 16.dp)) {\n                        Text(\"Log in to NetBanking\",")

# 4. Remove defaultMinSize which is unnecessary now
content = content.replace("                .weight(1f)\n                .defaultMinSize(minHeight = 500.dp),", "                .weight(1f),")

with open("app/src/main/java/com/example/ui/screens/AuthScreens.kt", "w") as f:
    f.write(content)
