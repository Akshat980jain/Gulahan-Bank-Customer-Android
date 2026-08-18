package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen(onNavigateBack: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Credits", "Debits")

    val allTransactions = listOf(
        TransactionData("1", "Netflix", "Entertainment", "Today, 10:30 AM", -649.0, Icons.Outlined.PlayArrow),
        TransactionData("2", "Salary", "Income", "Aug 13, 09:00 AM", 45000.0, Icons.Outlined.WorkOutline),
        TransactionData("3", "UPI Transfer to Vinay", "Transfer", "Aug 13, 02:15 PM", -2500.0, Icons.Outlined.Person),
        TransactionData("4", "Amazon.in", "Shopping", "Aug 12, 11:20 AM", -1450.0, Icons.Outlined.ShoppingCart),
        TransactionData("5", "Dividend Payout", "Investments", "Aug 10, 10:00 AM", 350.0, Icons.Outlined.WorkOutline),
        TransactionData("6", "Electricity Bill", "Utilities", "Aug 09, 06:45 PM", -1240.0, Icons.Outlined.PlayArrow)
    )

    val filteredTransactions = allTransactions.filter {
        val matchesSearch = it.title.contains(searchQuery, ignoreCase = true) || it.category.contains(searchQuery, ignoreCase = true)
        val matchesFilter = when (selectedFilter) {
            "Credits" -> it.amount > 0
            "Debits" -> it.amount < 0
            else -> true
        }
        matchesSearch && matchesFilter
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Recent Transactions", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold, fontSize = 20.sp) },
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
            SearchBarUI(searchQuery = searchQuery, onSearchQueryChange = { searchQuery = it })
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filters.forEach { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredTransactions) { transaction ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                    ) {
                        TransactionItem(
                            icon = transaction.icon,
                            title = transaction.title,
                            subtitle = "${transaction.category} • ${transaction.date}",
                            amount = transaction.amount,
                            isPositive = transaction.amount > 0
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBarUI(searchQuery: String, onSearchQueryChange: (String) -> Unit) {
    TextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        placeholder = { Text("Search transactions...", color = MaterialTheme.colorScheme.onSurfaceVariant) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = MaterialTheme.shapes.medium
    )
}

data class TransactionData(val id: String, val title: String, val category: String, val date: String, val amount: Double, val icon: ImageVector)
