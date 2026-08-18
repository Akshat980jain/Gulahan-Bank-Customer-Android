with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    content = f.read()

new_routes = """                            // Auth Screens
                            composable("login") {
                                LoginScreen(
                                    onLoginSuccess = { navController.navigate("home") { popUpTo("login") { inclusive = true } } },
                                    onNavigateToSignUp = { navController.navigate("signup") }
                                )
                            }
                            composable("signup") {
                                SignUpScreen(
                                    onSignUpSuccess = { navController.navigate("home") { popUpTo("signup") { inclusive = true } } },
                                    onNavigateToLogin = { navController.popBackStack() }
                                )
                            }

                            // Nested screens"""

content = content.replace("                            // Nested screens", new_routes)

import_to_add = "import com.example.ui.screens.SignUpScreen\nimport com.example.ui.screens.LoginScreen"
if "import com.example.ui.screens.LoginScreen" not in content:
    content = content.replace("import com.example.ui.screens.SplashScreen", f"import com.example.ui.screens.SplashScreen\n{import_to_add}")

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(content)
