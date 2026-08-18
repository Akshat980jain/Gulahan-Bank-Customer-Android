import re

with open("app/src/main/java/com/example/ui/screens/AuthScreens.kt", "r") as f:
    content = f.read()

# 1. Fix Top Header Spacing for both screens
# Login Screen Header Fixes
content = content.replace('.padding(top = 24.dp)', '.padding(top = 12.dp)')
content = content.replace('.padding(top = 16.dp)', '.padding(top = 8.dp)')
content = content.replace('Spacer(modifier = Modifier.height(24.dp))', 'Spacer(modifier = Modifier.height(12.dp))')
content = content.replace('Spacer(modifier = Modifier.height(16.dp))', 'Spacer(modifier = Modifier.height(8.dp))')
content = content.replace('Text("Secure Digital\\nBanking Portal", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold, lineHeight = 34.sp)', 'Text("Secure Digital\\nBanking Portal", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold, lineHeight = 30.sp)')
content = content.replace('Text("Open Your Digital\\nNet Banking Account", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold, lineHeight = 34.sp)', 'Text("Open Your Digital\\nNet Banking Account", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold, lineHeight = 30.sp)')
content = content.replace('Text("Access your deposit accounts, manage cards, initiate instant transfers, and track investments with complete security.", color = Color.LightGray, fontSize = 14.sp)', 'Text("Access your deposit accounts, manage cards, initiate instant transfers, and track investments with complete security.", color = Color.LightGray, fontSize = 12.sp, lineHeight = 16.sp)')
content = content.replace('Spacer(modifier = Modifier.height(32.dp))', 'Spacer(modifier = Modifier.height(16.dp))')

# 2. Fix Form Surface Padding
content = content.replace('.padding(24.dp)', '.padding(horizontal = 20.dp, vertical = 16.dp)')

# 3. Fix Internal Form Spacing
content = content.replace('Spacer(modifier = Modifier.height(8.dp))', 'Spacer(modifier = Modifier.height(4.dp))')
content = content.replace('Spacer(modifier = Modifier.height(20.dp))', 'Spacer(modifier = Modifier.height(12.dp))')
content = content.replace('.padding(vertical = 10.dp)', '.padding(vertical = 8.dp)')

# 4. Button sizes
content = content.replace('.height(56.dp)', '.height(50.dp)')

with open("app/src/main/java/com/example/ui/screens/AuthScreens.kt", "w") as f:
    f.write(content)
