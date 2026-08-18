with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    content = f.read()

fab_code = """        bottomBar = {
            MainBottomNavigation(currentScreen, onNavigate)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove); onNavigate("ai_advisor") },
                icon = { Icon(Icons.Outlined.AutoAwesome, contentDescription = "AI Advisor") },
                text = { Text("AI Advisor", fontWeight = FontWeight.SemiBold) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            )
        }"""

content = content.replace("""        bottomBar = {
            MainBottomNavigation(currentScreen, onNavigate)
        }""", fab_code)

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
    f.write(content)
