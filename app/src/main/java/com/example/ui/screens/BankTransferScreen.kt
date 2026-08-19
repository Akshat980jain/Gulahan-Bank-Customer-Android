package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat

class CurrencyVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text
        if (originalText.isEmpty()) return TransformedText(text, OffsetMapping.Identity)
        
        val formatted = try {
            val parsed = originalText.toLong()
            DecimalFormat("#,###").format(parsed)
        } catch (e: Exception) {
            originalText
        }
        
        return TransformedText(
            AnnotatedString(formatted),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (originalText.isEmpty()) return 0
                    var commaCount = 0
                    for (i in 0 until offset) {
                        // Calculate commas that would be added
                        val digitsFromRight = originalText.length - i
                        if (digitsFromRight > 0 && digitsFromRight % 3 == 0) {
                            commaCount++
                        }
                    }
                    val totalCommas = Math.max(0, (originalText.length - 1) / 3)
                    val offsetFromRight = originalText.length - offset
                    val commasAfterOffset = Math.max(0, (offsetFromRight - 1) / 3)
                    return offset + (totalCommas - commasAfterOffset)
                }

                override fun transformedToOriginal(offset: Int): Int {
                    var originalOffset = 0
                    var transformedIndex = 0
                    while (transformedIndex < offset && originalOffset < originalText.length) {
                        if (formatted[transformedIndex] != ',') {
                            originalOffset++
                        }
                        transformedIndex++
                    }
                    return originalOffset
                }
            }
        )
    }
}

class AccountNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text
        var out = ""
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i % 4 == 3 && i != 15) out += " "
        }
        
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 3) return offset
                if (offset <= 7) return offset + 1
                if (offset <= 11) return offset + 2
                if (offset <= 16) return offset + 3
                return 19
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 4) return offset
                if (offset <= 9) return offset - 1
                if (offset <= 14) return offset - 2
                if (offset <= 19) return offset - 3
                return 16
            }
        }
        
        return TransformedText(AnnotatedString(out), offsetMapping)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BankTransferScreen(onNavigateBack: () -> Unit, onNavigateSuccess: () -> Unit) {
    var accountNumber by remember { mutableStateOf("") }
    var ifscCode by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    val haptic = LocalHapticFeedback.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bank Transfer", fontSize = 20.sp, fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onNavigateBack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            OutlinedTextField(
                value = accountNumber,
                onValueChange = { if (it.length <= 16 && it.all { char -> char.isDigit() }) accountNumber = it },
                label = { Text("Account Number") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = AccountNumberVisualTransformation(),
                singleLine = true
            )

            OutlinedTextField(
                value = ifscCode,
                onValueChange = { if (it.length <= 11) ifscCode = it.uppercase() },
                label = { Text("IFSC Code") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Characters),
                singleLine = true
            )

            OutlinedTextField(
                value = amount,
                onValueChange = { if (it.all { char -> char.isDigit() }) amount = it },
                label = { Text("Amount (₹)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = CurrencyVisualTransformation(),
                singleLine = true,
                leadingIcon = { Text("₹", modifier = Modifier.padding(start = 16.dp, end = 8.dp), fontSize = 18.sp) }
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onNavigateSuccess()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.large,
                enabled = accountNumber.length >= 8 && ifscCode.length == 11 && amount.isNotEmpty()
            ) {
                Text("Proceed to Transfer", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
