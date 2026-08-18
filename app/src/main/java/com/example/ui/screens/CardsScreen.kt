package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Nfc
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.LocalAtm
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.components.MainBottomNavigation

@Composable
fun CardControlScreen(currentScreen: String, onNavigate: (String) -> Unit) {
    var isCardFrozen by remember { mutableStateOf(false) }
    var onlineEnabled by remember { mutableStateOf(true) }
    var nfcEnabled by remember { mutableStateOf(true) }
    var atmEnabled by remember { mutableStateOf(true) }
    var intlEnabled by remember { mutableStateOf(false) }
    var spendLimit by remember { mutableFloatStateOf(1000f) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { MainBottomNavigation(currentScreen, onNavigate) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            
            // Visual Card Representation
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize().padding(24.dp)) {
                    Text("Gulshan E-Bank", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.TopStart))
                    Text("VISA", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Black, modifier = Modifier.align(Alignment.TopEnd))
                    
                    Column(modifier = Modifier.align(Alignment.BottomStart)) {
                        Text("**** **** **** 4281", color = Color.White, fontSize = 20.sp, letterSpacing = 2.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                            Text("AKSHAT JAIN", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.labelMedium)
                            Text("12/28", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
            }

            Text(text = "CARD CONTROLS & LIMITS", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))

            // Card Freeze Emergency Switch
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = if (isCardFrozen) MaterialTheme.colorScheme.error.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, if (isCardFrozen) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = if (isCardFrozen) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = "Freeze Card", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Instantly block all card transactions", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp)
                        }
                    }
                    Switch(checked = isCardFrozen, onCheckedChange = { isCardFrozen = it })
                }
            }

            // Toggle Controls List
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
            ) {
                Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    ControlToggleItem(icon = Icons.Default.ShoppingCart, title = "Online E-Commerce", isChecked = onlineEnabled, onCheckedChange = { onlineEnabled = it })
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                    ControlToggleItem(icon = Icons.Default.Nfc, title = "Contactless Tap & Pay", isChecked = nfcEnabled, onCheckedChange = { nfcEnabled = it })
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                    ControlToggleItem(icon = Icons.Default.LocalAtm, title = "ATM Withdrawals", isChecked = atmEnabled, onCheckedChange = { atmEnabled = it })
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                    ControlToggleItem(icon = Icons.Default.Public, title = "International Transactions", isChecked = intlEnabled, onCheckedChange = { intlEnabled = it })
                }
            }

            // Spending Limit Slider
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Monthly Spending Limit", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                        Text(text = "$${spendLimit.toInt()}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Slider(
                        value = spendLimit,
                        onValueChange = { spendLimit = it },
                        valueRange = 100f..10000f,
                        steps = 99,
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary,
                            inactiveTrackColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    Text(text = "Max Limit: $10,000", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp, modifier = Modifier.align(Alignment.End))
                }
            }
        }
    }
}

@Composable
fun ControlToggleItem(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
        }
        Switch(checked = isChecked, onCheckedChange = onCheckedChange)
    }
}
