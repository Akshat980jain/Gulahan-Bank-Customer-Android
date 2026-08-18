package com.example.ui.screens

import androidx.compose.material3.MaterialTheme

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityCenterScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Security Center", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp)) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = SuccessGreen.copy(alpha = 0.1f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, SuccessGreen)
            ) {
                Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.VerifiedUser, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(32.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Account is Secure", color = SuccessGreen, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("No recent suspicious activity.", color = MaterialTheme.colorScheme.onSurface, fontSize = 14.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text("Active Sessions", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(16.dp))
            SessionItem("Samsung Galaxy S23", "Active now • Bangalore, India", Icons.Outlined.Smartphone)
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.outline)
            SessionItem("Windows PC - Chrome", "Last active 2 hrs ago • Bangalore, India", Icons.Outlined.Computer)
        }
    }
}

@Composable
fun SessionItem(device: String, details: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(device, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium, fontSize = 16.sp)
            Text(details, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
        }
        Text("Log Out", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePinScreen(onNavigateBack: () -> Unit) {
    var oldPin by remember { mutableStateOf("") }
    var newPin by remember { mutableStateOf("") }
    
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Change PIN", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp)) {
            Text("Create a new 4-digit PIN for your transactions.", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = oldPin,
                onValueChange = { if (it.length <= 4) oldPin = it },
                label = { Text("Current PIN") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.NumberPassword),
                visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = newPin,
                onValueChange = { if (it.length <= 4) newPin = it },
                label = { Text("New PIN") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.NumberPassword),
                visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Update PIN", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiometricLoginScreen(onNavigateBack: () -> Unit) {
    var isFingerprintEnabled by remember { mutableStateOf(true) }
    var isFaceIdEnabled by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Biometric Login", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp)) {
            Text("Enable biometrics for faster and more secure logins.", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Fingerprint, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Fingerprint Login", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }
                Switch(checked = isFingerprintEnabled, onCheckedChange = { isFingerprintEnabled = it }, colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary, checkedTrackColor = MaterialTheme.colorScheme.primaryContainer))
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = MaterialTheme.colorScheme.outline)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Face, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Face ID Login", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }
                Switch(checked = isFaceIdEnabled, onCheckedChange = { isFaceIdEnabled = it }, colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary, checkedTrackColor = MaterialTheme.colorScheme.primaryContainer))
            }
        }
    }
}
