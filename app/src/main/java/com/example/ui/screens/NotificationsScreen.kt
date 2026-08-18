package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(onNavigateBack: () -> Unit) {
    var selectedTab by remember { mutableStateOf("All") }
    val tabs = listOf("All", "Transactions", "Security")

    val allNotifications = listOf(
        NotificationItem("security", "New login detected", "A new login was detected on a Chrome browser in Mumbai.", "2 mins ago", Icons.Outlined.Lock, MaterialTheme.colorScheme.error),
        NotificationItem("transaction", "Money Received", "₹2,500 transferred successfully from Vinay.", "1 hour ago", Icons.Outlined.Payment, SuccessGreen),
        NotificationItem("transaction", "Bill Payment Due", "Electricity bill of ₹1,240 is due in 3 days.", "5 hours ago", Icons.Outlined.NotificationsActive, MaterialTheme.colorScheme.primary),
        NotificationItem("security", "Password Changed", "Your account password was recently updated.", "1 day ago", Icons.Outlined.Lock, MaterialTheme.colorScheme.primary),
        NotificationItem("transaction", "Subscription Renewal", "Netflix subscription of ₹649 has been processed.", "2 days ago", Icons.Outlined.Payment, MaterialTheme.colorScheme.onSurface)
    )

    val filteredNotifications = if (selectedTab == "All") allNotifications else allNotifications.filter {
        if (selectedTab == "Transactions") it.type == "transaction" else it.type == "security"
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Notifications", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onSurface)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tabs.forEach { tab ->
                    FilterChip(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        label = { Text(tab) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredNotifications) { notification ->
                    NotificationRow(notification)
                }
            }
        }
    }
}

data class NotificationItem(val type: String, val title: String, val description: String, val time: String, val icon: ImageVector, val iconColor: Color)

@Composable
fun NotificationRow(item: NotificationItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(item.iconColor.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(item.icon, contentDescription = null, tint = item.iconColor, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = item.title, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Text(text = item.time, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = item.description, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp, lineHeight = 20.sp)
        }
    }
}
