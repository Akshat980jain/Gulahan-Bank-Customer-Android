package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.automirrored.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.MainBottomNavigation
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreScreen(currentScreen: String, onNavigate: (String) -> Unit) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Menu", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = { MainBottomNavigation(currentScreen, onNavigate) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                Row(
                    modifier = Modifier.padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("A", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Akshat Jain", color = MaterialTheme.colorScheme.onSurface, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("akshat980jain@gmail.com", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("View Profile", color = MaterialTheme.colorScheme.primary, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }
            item { HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)) }
            
            item { Text("Account", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)) }
            item { SettingsMenuItem(Icons.Outlined.AccountCircle, "Personal Information") { onNavigate("personal_info") } }
            item { SettingsMenuItem(Icons.Outlined.VerifiedUser, "KYC Verification") { onNavigate("kyc") } }
            item { SettingsMenuItem(Icons.Outlined.AccountBalance, "Linked Accounts") { onNavigate("linked_accounts") } }
            item { SettingsMenuItem(Icons.Outlined.History, "Transaction History") { onNavigate("transactions") } }
            
            item { Text("Security", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)) }
            item { SettingsMenuItem(Icons.Outlined.Security, "Security Center") { onNavigate("security_center") } }
            item { SettingsMenuItem(Icons.Outlined.VpnKey, "Change PIN") { onNavigate("change_pin") } }
            item { SettingsMenuItem(Icons.Outlined.Fingerprint, "Biometric Login") { onNavigate("biometric_login") } }
            
            item { Text("Preferences", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)) }
            item { SettingsMenuItem(Icons.Outlined.Notifications, "Notifications") { onNavigate("notifications") } }
            item { SettingsMenuItem(Icons.Outlined.Language, "Language & Region") { onNavigate("language_region") } }
            item { SettingsMenuItem(Icons.Outlined.DarkMode, "Dark Mode") { onNavigate("dark_mode") } }
            
            item { Text("Support", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)) }
            item { SettingsMenuItem(Icons.AutoMirrored.Outlined.HelpOutline, "Help & Support") { onNavigate("help_support") } }
            item { SettingsMenuItem(Icons.AutoMirrored.Outlined.Article, "Terms of Service") { onNavigate("terms_service") } }
            
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)) }
            
            item { SettingsMenuItem(Icons.AutoMirrored.Outlined.Logout, "Logout", tint = MaterialTheme.colorScheme.error) { showLogoutDialog = true } }
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }

        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                icon = { Icon(Icons.AutoMirrored.Outlined.Logout, contentDescription = null, tint = MaterialTheme.colorScheme.error) },
                title = { Text("Sign Out", fontWeight = FontWeight.Bold) },
                text = { Text("Are you sure you want to securely sign out of your workstation? You will need to re-enter your credentials to access your account.") },
                confirmButton = {
                    Button(
                        onClick = {
                            showLogoutDialog = false
                            onNavigate("login")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Sign Out", color = MaterialTheme.colorScheme.onError)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutDialog = false }) {
                        Text("Cancel", color = MaterialTheme.colorScheme.onSurface)
                    }
                },
                shape = RoundedCornerShape(16.dp),
                containerColor = MaterialTheme.colorScheme.surface
            )
        }
    }
}


@Composable
fun SettingsMenuItem(icon: ImageVector, title: String, tint: Color = MaterialTheme.colorScheme.onSurface, onClick: () -> Unit = {}) {
    val haptic = LocalHapticFeedback.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { 
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                onClick() 
            }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, color = tint, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(20.dp))
    }
}
