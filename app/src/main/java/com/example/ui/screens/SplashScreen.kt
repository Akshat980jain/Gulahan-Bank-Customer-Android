package com.example.ui.screens
import androidx.compose.material3.MaterialTheme
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(onNavigate: (String) -> Unit) {
    val scale = remember { Animatable(0.5f) }
    val alpha = remember { Animatable(0f) }
    val textOffset = remember { Animatable(50f) }

    LaunchedEffect(Unit) {
        // Animate logo scale with a bounce
        launch {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
        
        // Fade in logo and background elements
        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000)
            )
        }
        
        // Staggered slide up for the text
        delay(300)
        launch {
            textOffset.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
            )
        }
        
        delay(2000)
        onNavigate("login")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A192F), // Dark deep blue
                        Color(0xFF020C1B)  // Even darker slate blue
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo background circle with gradient
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .scale(scale.value)
                    .alpha(alpha.value)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(MaterialTheme.colorScheme.primary, Color(0xFF4FC3F7))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.AccountBalance,
                    contentDescription = "Bank Logo",
                    tint = Color.White,
                    modifier = Modifier.size(54.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // App Title
            Text(
                text = "GULSHAN E-BANK",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp,
                modifier = Modifier
                    .alpha(alpha.value)
                    .offset(y = textOffset.value.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Subtitle
            Text(
                text = "SECURE DIGITAL BANKING",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 4.sp,
                modifier = Modifier
                    .alpha(alpha.value)
                    .offset(y = textOffset.value.dp)
            )
        }
    }
}
