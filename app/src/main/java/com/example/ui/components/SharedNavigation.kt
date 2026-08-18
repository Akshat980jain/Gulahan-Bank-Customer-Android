package com.example.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun MainBottomNavigation(currentScreen: String, onNavigate: (String) -> Unit) {
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    NavigationBar(
        modifier = androidx.compose.ui.Modifier.height(54.dp + bottomPadding),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 12.dp,
        windowInsets = WindowInsets(0.dp)
    ) {
        NavBarItem("home", "Home", Icons.Outlined.Home, Icons.Filled.Home, currentScreen, onNavigate)
        NavBarItem("payments", "Payments", Icons.Outlined.SwapHoriz, Icons.Filled.SwapHoriz, currentScreen, onNavigate)
        NavBarItem("scan", "Scan", Icons.Outlined.QrCodeScanner, Icons.Filled.QrCodeScanner, currentScreen, onNavigate)
        NavBarItem("cards", "Cards", Icons.Outlined.CreditCard, Icons.Filled.CreditCard, currentScreen, onNavigate)
        NavBarItem("more", "Menu", Icons.Outlined.Menu, Icons.Filled.Menu, currentScreen, onNavigate)
    }
}

@Composable
fun RowScope.NavBarItem(id: String, label: String, unselectedIcon: ImageVector, selectedIcon: ImageVector, currentScreen: String, onNavigate: (String) -> Unit) {
    val selected = currentScreen == id
    val haptic = LocalHapticFeedback.current
    NavigationBarItem(
        selected = selected,
        onClick = { 
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            onNavigate(id) 
        },
        icon = { Icon(if (selected) selectedIcon else unselectedIcon, contentDescription = label) },
        label = { Text(label, fontSize = 10.sp, fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium) },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}
