package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.automirrored.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.ui.theme.*
import com.example.ui.components.MainBottomNavigation

@Composable
fun HomeScreen(
    currentScreen: String,
    onNavigate: (String) -> Unit,
    userName: String = "Akshat Jain",
    totalAum: Double = 254890.0
) {
    var isBalanceVisible by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(true) }
    val haptic = LocalHapticFeedback.current

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1500)
        isLoading = false
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { DashboardTopBar(userName = userName, onNotificationClick = { onNavigate("notifications") }) },
        bottomBar = {
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
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp)
        ) {
            if (isLoading) {
                item { SkeletonBox(modifier = Modifier.fillMaxWidth().height(200.dp).padding(horizontal = 16.dp)) }
                item { SkeletonBox(modifier = Modifier.fillMaxWidth().height(120.dp).padding(horizontal = 16.dp)) }
                item { SkeletonBox(modifier = Modifier.fillMaxWidth().height(100.dp).padding(horizontal = 16.dp)) }
                item { SkeletonBox(modifier = Modifier.fillMaxWidth().height(180.dp).padding(horizontal = 16.dp)) }
            } else {
                item { NetWorthCard(totalAum, isBalanceVisible) { isBalanceVisible = !isBalanceVisible } }
                item { FinancialOverviewSection() }
                item { QuickActionsGrid({ onNavigate("scan") }, { onNavigate("cards") }, { onNavigate("payments") }, { onNavigate("add_money") }) }
                item { AccountsCarousel() }
                item { RecentTransactionsSection(onViewAll = { onNavigate("transactions") }) }
                item { SpendingAnalyticsSection() }
                item { UpcomingPaymentsSection() }
                
            }
        }
    }
}

@Composable
fun SkeletonBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
    )
}

@Composable
fun DashboardTopBar(userName: String, onNotificationClick: () -> Unit = {}) {
    val haptic = LocalHapticFeedback.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.AccountBalance, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = "Good morning, ${userName.split(" ").first()}", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "Here's your financial overview", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                onNotificationClick()
            }) {
                BadgedBox(badge = { Badge(containerColor = MaterialTheme.colorScheme.error) { Text("3", color = Color.White) } }) {
                    Icon(Icons.Outlined.Notifications, contentDescription = "Alerts", tint = MaterialTheme.colorScheme.onSurface)
                }
            }
            Spacer(modifier = Modifier.width(4.dp))
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .clickable { haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) },
                contentAlignment = Alignment.Center
            ) {
                Text(userName.take(1), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun NetWorthCard(totalAum: Double, isBalanceVisible: Boolean, onToggleVisibility: () -> Unit) {
    val haptic = LocalHapticFeedback.current
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = androidx.compose.ui.graphics.Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF0F172A), // Deep Navy
                        MaterialTheme.colorScheme.primary, // Primary Blue
                        Color(0xFF3B82F6) // Bright Blue
                    )
                )
            )
        ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Total Net Worth", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                Icon(
                    imageVector = if (isBalanceVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = "Toggle Balance",
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(22.dp).clickable { 
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onToggleVisibility() 
                    }
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = if (isBalanceVisible) "₹${String.format("%,.2f", totalAum)}" else "₹ ••••••••",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.width(12.dp))
                Surface(
                    color = SuccessGreen.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.TrendingUp, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "+12.4%", color = SuccessGreen, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = Color.White.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                NetWorthBreakdown("Cash", 154890.0, isBalanceVisible)
                NetWorthBreakdown("Investments", 100000.0, isBalanceVisible)
                NetWorthBreakdown("Credit", 0.0, isBalanceVisible)
            }
        }
        // Add subtle overlay circles for engaging visuals
        androidx.compose.foundation.Canvas(modifier = Modifier.matchParentSize()) {
            drawCircle(
                color = Color.White.copy(alpha = 0.05f),
                radius = size.width / 2.5f,
                center = androidx.compose.ui.geometry.Offset(size.width * 1.1f, size.height * -0.2f)
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.03f),
                radius = size.width / 3f,
                center = androidx.compose.ui.geometry.Offset(size.width * 0.9f, size.height * 1.1f)
            )
        }
        }
    }
}

@Composable
fun NetWorthBreakdown(label: String, amount: Double, isVisible: Boolean) {
    Column {
        Text(text = label, color = Color.White.copy(alpha = 0.7f), style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = if (isVisible) "₹${String.format("%,.0f", amount)}" else "₹ ••••",
            color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun FinancialOverviewSection() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text("Financial Overview", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OverviewCard(Modifier.weight(1f), "Income", 45000.0, "+8.2%", true, Icons.Outlined.ArrowDownward)
            OverviewCard(Modifier.weight(1f), "Expenses", 28450.0, "-4.1%", false, Icons.Outlined.ArrowUpward)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OverviewCard(Modifier.weight(1f), "Savings", 16550.0, "+12.5%", true, Icons.Outlined.Savings)
            OverviewCard(Modifier.weight(1f), "Investments", 100000.0, "+5.8%", true, Icons.AutoMirrored.Outlined.TrendingUp)
        }
    }
}

@Composable
fun OverviewCard(modifier: Modifier, title: String, amount: Double, trend: String, isPositive: Boolean, icon: ImageVector) {
    val trendColor = if (isPositive) SuccessGreen else MaterialTheme.colorScheme.error
    val trendBg = if (isPositive) SuccessGreen.copy(alpha = 0.1f) else MaterialTheme.colorScheme.error.copy(alpha = 0.1f)
    
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(32.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer), contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                }
                Surface(color = trendBg, shape = RoundedCornerShape(6.dp)) {
                    Text(text = trend, color = trendColor, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp))
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = title, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "₹${String.format("%,.0f", amount)}", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.headlineSmall)
        }
    }
}

@Composable
fun QuickActionsGrid(onScanPay: () -> Unit, onCards: () -> Unit, onTransfer: () -> Unit, onAddMoney: () -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text("Quick Actions", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            QuickActionButton(Icons.Outlined.QrCodeScanner, "Scan & Pay", onScanPay)
            QuickActionButton(Icons.AutoMirrored.Outlined.Send, "Transfer", onTransfer)
            QuickActionButton(Icons.Outlined.AccountBalanceWallet, "Add Money", onAddMoney)
            QuickActionButton(Icons.Outlined.CreditCard, "Cards", onCards)
        }
    }
}

@Composable
fun QuickActionButton(icon: ImageVector, label: String, onClick: () -> Unit) {
    val haptic = LocalHapticFeedback.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { 
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            onClick() 
        }.width(76.dp)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.surface)
                .padding(1.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = label, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(26.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Medium, maxLines = 1)
    }
}

@Composable
fun AccountsCarousel() {
    Column {
        PaddingValues(horizontal = 16.dp).let {
            Row(modifier = Modifier.fillMaxWidth().padding(it), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("My Accounts", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.headlineSmall)
                Icon(Icons.Default.ArrowForward, contentDescription = "View All", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            item { AccountCard("Savings Account", "•••• 2005", 154890.0, "Active") }
            item { AccountCard("Salary Account", "•••• 4091", 45000.0, "Active") }
            item { AccountCard("Fixed Deposit", "•••• 9982", 100000.0, "Matures in 6 mo") }
        }
    }
}

@Composable
fun AccountCard(type: String, number: String, balance: Double, status: String) {
    Card(
        modifier = Modifier.width(280.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Column {
                    Text(text = type, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = number, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
                }
                Icon(Icons.Default.MoreVert, contentDescription = "Options", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Available Balance", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "₹${String.format("%,.2f", balance)}",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Surface(color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f), shape = RoundedCornerShape(6.dp)) {
                    Text(text = status, color = MaterialTheme.colorScheme.primary, fontSize = 11.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                }
            }
        }
    }
}

@Composable
fun RecentTransactionsSection(onViewAll: () -> Unit = {}) {
    val haptic = LocalHapticFeedback.current
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Recent Transactions", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.headlineSmall)
            Text("View All", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, modifier = Modifier.clickable { 
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                onViewAll() 
            })
        }
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            modifier = Modifier.fillMaxWidth().height(260.dp), // Fixed height for internal scrolling
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item { TransactionItem(Icons.Outlined.PlayArrow, "Netflix", "Entertainment · Today", -649.0) }
                item { HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)) }
                item { TransactionItem(Icons.Outlined.WorkOutline, "Salary", "Income · Aug 13", 45000.0, isPositive = true) }
                item { HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)) }
                item { TransactionItem(Icons.Outlined.Person, "UPI Transfer to Vinay", "Transfer · Aug 13", -2500.0) }
                item { HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)) }
                item { TransactionItem(Icons.Outlined.ShoppingCart, "Amazon", "Shopping · Aug 12", -1200.0) }
                item { HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)) }
                item { TransactionItem(Icons.Outlined.Restaurant, "Zomato", "Food · Aug 11", -450.0) }
            }
        }
    }
}

@Composable
fun TransactionItem(icon: ImageVector, title: String, subtitle: String, amount: Double, isPositive: Boolean = false) {
    val haptic = LocalHapticFeedback.current
    Row(modifier = Modifier.fillMaxWidth().clickable{ haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(44.dp).clip(CircleShape).background(if(isPositive) SuccessGreen.copy(alpha=0.1f) else MaterialTheme.colorScheme.primaryContainer), contentAlignment = Alignment.Center) {
            Icon(icon, contentDescription = null, tint = if(isPositive) SuccessGreen else MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
        }
        Text(
            text = "${if(isPositive) "+" else "-"}₹${String.format("%,.0f", Math.abs(amount))}",
            color = if (isPositive) SuccessGreen else MaterialTheme.colorScheme.onSurface,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SpendingAnalyticsSection() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text("Spending Analytics", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("This Month", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("₹28,450", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.displaySmall)
                    }
                    Surface(color = SuccessGreen.copy(alpha = 0.1f), shape = MaterialTheme.shapes.large) {
                        Text("8% less than last month", color = SuccessGreen, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Medium, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp))
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
                
                // Donut Chart built with native Compose Canvas
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    val primaryColor = MaterialTheme.colorScheme.primary
                    androidx.compose.foundation.Canvas(modifier = Modifier.size(160.dp)) {
                        val strokeWidth = 30.dp.toPx()
                        var startAngle = -90f
                        
                        val proportions = listOf(0.4f, 0.25f, 0.2f, 0.15f)
                        val colors = listOf(primaryColor, Color(0xFF6366F1), Color(0xFF10B981), Color(0xFFF59E0B))
                        
                        proportions.forEachIndexed { index, prop ->
                            val sweepAngle = prop * 360f
                            drawArc(
                                color = colors[index],
                                startAngle = startAngle,
                                sweepAngle = sweepAngle,
                                useCenter = false,
                                style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth, cap = androidx.compose.ui.graphics.StrokeCap.Round),
                                size = androidx.compose.ui.geometry.Size(size.width, size.height)
                            )
                            startAngle += sweepAngle
                        }
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Total", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelMedium)
                        Text("100%", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.headlineSmall)
                    }
                }
                
                Spacer(modifier = Modifier.height(40.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    AnalyticsLegend("Food", MaterialTheme.colorScheme.primary)
                    AnalyticsLegend("Shopping", Color(0xFF6366F1))
                    AnalyticsLegend("Bills", Color(0xFF10B981))
                    AnalyticsLegend("Other", Color(0xFFF59E0B))
                }
            }
        }
    }
}

@Composable
fun AnalyticsLegend(label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(color))
        Spacer(modifier = Modifier.width(6.dp))
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun UpcomingPaymentsSection() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text("Upcoming Payments", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column {
                UpcomingPaymentItem("Electricity Bill", "Aug 17", 1240.0)
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                UpcomingPaymentItem("Credit Card", "Aug 20", 8500.0)
            }
        }
    }
}

@Composable
fun UpcomingPaymentItem(title: String, date: String, amount: Double) {
    val haptic = LocalHapticFeedback.current
    Row(modifier = Modifier.fillMaxWidth().clickable{ haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }.padding(24.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(44.dp).clip(MaterialTheme.shapes.medium).background(MaterialTheme.colorScheme.primaryContainer), contentAlignment = Alignment.Center) {
                Icon(Icons.Outlined.FlashOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(2.dp))
                Text(date, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
            }
        }
        Button(
            onClick = { haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer, contentColor = MaterialTheme.colorScheme.primary),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
            modifier = Modifier.height(40.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Pay ₹${String.format("%,.0f", amount)}", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
        }
    }
}


