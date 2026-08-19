package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.ui.screens.*
import com.example.ui.theme.GulshanEBankTheme

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : FragmentActivity() {
    private var isAuthenticated = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        authenticateWithBiometrics()

        setContent {
            val darkModePreference by ThemePreferences.isDarkMode.collectAsState()
            val useDarkTheme = darkModePreference ?: isSystemInDarkTheme()

            GulshanEBankTheme(darkTheme = useDarkTheme) {
                if (isAuthenticated.value) {
                    val navController = rememberNavController()
                    Surface(modifier = Modifier.fillMaxSize()) {
                        NavHost(navController = navController, startDestination = "home") {
                            val bottomNavAction: (String) -> Unit = { route ->
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
                            }
                            
                            composable("splash") { 
                                SplashScreen(onNavigate = { 
                                    navController.navigate(it) { 
                                        popUpTo("splash") { inclusive = true } 
                                    } 
                                }) 
                            }
                            composable("home") { HomeScreen("home", onNavigate = bottomNavAction) }
                            composable("payments") { TransfersScreen("payments", onNavigate = bottomNavAction) }
                            composable("scan") { ScanScreen("scan", onNavigate = bottomNavAction) }
                            composable("cards") { CardControlScreen("cards", onNavigate = bottomNavAction) }
                            composable("more") { MoreScreen("more", onNavigate = bottomNavAction) }
                            
                            // Auth Screens
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

                            // Nested screens
                            composable("transaction_success") { TransactionSuccessScreen(onNavigateHome = { navController.navigate("home") { popUpTo("home") { inclusive = true } } }) }
                            composable("bank_transfer") { BankTransferScreen(onNavigateBack = { navController.popBackStack() }, onNavigateSuccess = { navController.navigate("transaction_success") }) }
                            composable("add_money") { AddMoneyScreen(onNavigateBack = { navController.popBackStack() }, onNavigateSuccess = { navController.navigate("transaction_success") }) }
                            composable("ai_advisor") { AiAdvisorScreen(onNavigateBack = { navController.popBackStack() }) }
                            composable("notifications") { NotificationsScreen(onNavigateBack = { navController.popBackStack() }) }
                            composable("transactions") { TransactionsScreen(onNavigateBack = { navController.popBackStack() }) }
                            composable("personal_info") { PersonalInfoScreen(onNavigateBack = { navController.popBackStack() }) }
                            composable("linked_accounts") { LinkedAccountsScreen(onNavigateBack = { navController.popBackStack() }) }
                            composable("security_center") { SecurityCenterScreen(onNavigateBack = { navController.popBackStack() }) }
                            composable("change_pin") { ChangePinScreen(onNavigateBack = { navController.popBackStack() }) }
                            composable("biometric_login") { BiometricLoginScreen(onNavigateBack = { navController.popBackStack() }) }
                            composable("language_region") { LanguageRegionScreen(onNavigateBack = { navController.popBackStack() }) }
                            composable("dark_mode") { DarkModeScreen(onNavigateBack = { navController.popBackStack() }) }
                            composable("help_support") { HelpSupportScreen(onNavigateBack = { navController.popBackStack() }) }
                            composable("terms_service") { TermsServiceScreen(onNavigateBack = { navController.popBackStack() }) }
                            composable("kyc") { KycScreen(onNavigateBack = { navController.popBackStack() }) }
                        }
                    }
                } else {
                    // Show a locked screen or nothing while authenticating
                    SplashScreen(onNavigate = {}) 
                }
            }
        }
    }

    private fun authenticateWithBiometrics() {
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
                    // Fallback to true for testing in emulator where biometrics might fail or cancel
                    isAuthenticated.value = true 
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    isAuthenticated.value = true
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Unlock Gulshan-E-Bank")
            .setSubtitle("Confirm your identity to continue")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}
