import re

with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    content = f.read()

old_nav_action = """val bottomNavAction: (String) -> Unit = { route ->
                                navController.navigate(route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }"""

new_nav_action = """val bottomNavAction: (String) -> Unit = { route ->
                                if (route in listOf("home", "payments", "scan", "cards", "more")) {
                                    navController.navigate(route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                } else {
                                    navController.navigate(route)
                                }
                            }"""

content = content.replace(old_nav_action, new_nav_action)

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(content)
