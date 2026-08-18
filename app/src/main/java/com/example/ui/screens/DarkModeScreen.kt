package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ThemePreferences
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DarkModeScreen(onNavigateBack: () -> Unit) {
    val currentMode by ThemePreferences.isDarkMode.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Dark Mode", fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ThemeOption("System Default", currentMode == null) { ThemePreferences.isDarkMode.value = null }
            ThemeOption("Light Mode", currentMode == false) { ThemePreferences.isDarkMode.value = false }
            ThemeOption("Dark Mode", currentMode == true) { ThemePreferences.isDarkMode.value = true }
        }
    }
}

@Composable
fun ThemeOption(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp, modifier = Modifier.weight(1f))
        if (isSelected) {
            Icon(Icons.Default.Check, contentDescription = "Selected", tint = MaterialTheme.colorScheme.primary)
        }
    }
}
