package com.example.ui.screens

import androidx.compose.material3.MaterialTheme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.example.ui.theme.*

@Composable
fun TransactionSuccessScreen(onNavigateHome: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(com.example.R.raw.success_anim))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(200.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Transaction Successful!",
            fontSize = 24.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Your transfer has been processed.",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Button(
            onClick = onNavigateHome,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth(0.8f).height(50.dp)
        ) {
            Text("Back to Home", fontSize = 16.sp)
        }
    }
}
