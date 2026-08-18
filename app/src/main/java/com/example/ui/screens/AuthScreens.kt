package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.R
import com.example.ui.theme.PrimaryBlue

val DarkNavy = Color(0xFF0F172A)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    var customerId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkNavy)
    ) {
        // Top Branding Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .padding(top = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.logo_recreated_1786856553161),
                    contentDescription = "Logo",
                    modifier = Modifier.size(36.dp).clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Gulshan EBank", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("NET BANKING PORTAL", color = Color.Gray, fontSize = 10.sp, letterSpacing = 1.sp)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color(0xFF064E3B), RoundedCornerShape(16.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF34D399), modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("256-Bit SSL Encrypted", color = Color(0xFF34D399), fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text("Secure Digital\nBanking Portal", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold, lineHeight = 30.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Access your deposit accounts, manage cards, initiate instant transfers, and track investments with complete security.", color = Color.LightGray, fontSize = 12.sp, lineHeight = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Bottom Form Section
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f).padding(end = 16.dp)) {
                        Text("Log in to NetBanking", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Text("Select your account type and enter your credentials", fontSize = 12.sp, color = Color.Gray)
                    }
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.clickable { }
                    ) {
                        Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Outlined.QrCodeScanner, contentDescription = "Scan QR", tint = PrimaryBlue, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Scan QR", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))

                // Segmented Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val tabs = listOf("NetBanking", "Credit Card", "Loan")
                    tabs.forEachIndexed { index, title ->
                        val isSelected = selectedTab == index
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) MaterialTheme.colorScheme.surface else Color.Transparent)
                                .clickable { selectedTab = index }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = title,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isSelected) MaterialTheme.colorScheme.onSurface else Color.Gray
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Customer ID
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("CUSTOMER ID / EMAIL ID", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    Text("Forgot Customer ID?", fontSize = 10.sp, fontWeight = FontWeight.Medium, color = PrimaryBlue, modifier = Modifier.clickable { })
                }
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = customerId,
                    onValueChange = { customerId = it },
                    placeholder = { Text("Enter Customer ID or Email", fontSize = 14.sp) },
                    leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = null, tint = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = PrimaryBlue
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Password
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("IPIN / PASSWORD", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    Text("Reset Password?", fontSize = 10.sp, fontWeight = FontWeight.Medium, color = PrimaryBlue, modifier = Modifier.clickable { })
                }
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Enter Password", fontSize = 14.sp) },
                    leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null, tint = Color.Gray) },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(if (passwordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff, contentDescription = null, tint = Color.Gray)
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = PrimaryBlue
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onLoginSuccess,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkNavy)
                ) {
                    Text("Secure Login", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Don't have a NetBanking account?", fontSize = 12.sp, color = Color.Gray)
                    TextButton(onClick = onNavigateToSignUp) {
                        Text("Register New Account", fontSize = 12.sp, color = PrimaryBlue, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkNavy)
    ) {
        // Top Branding Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .padding(top = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onNavigateToLogin() }
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.Gray, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Back to NetBanking Login", color = Color.Gray, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.logo_recreated_1786856553161),
                    contentDescription = "Logo",
                    modifier = Modifier.size(36.dp).clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Gulshan EBank", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("DIGITAL ONBOARDING", color = Color.Gray, fontSize = 10.sp, letterSpacing = 1.sp)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color(0xFF064E3B), RoundedCornerShape(16.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF34D399), modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Instant Customer ID Generation", color = Color(0xFF34D399), fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text("Open Your Digital\nNet Banking Account", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold, lineHeight = 30.sp)
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Bottom Form Section
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text("Create Customer Profile", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Text("Enter your details to generate your NetBanking ID", fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(12.dp))

                // Full Name
                Text("FULL LEGAL NAME", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    placeholder = { Text("As printed on Aadhaar / PAN", fontSize = 14.sp) },
                    leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = null, tint = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = PrimaryBlue
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Email
                Text("EMAIL ADDRESS", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("For OTPs & alerts", fontSize = 14.sp) },
                    leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null, tint = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = PrimaryBlue
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Passwords
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("PASSWORD", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            placeholder = { Text("Min 6 chars", fontSize = 14.sp) },
                            leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null, tint = Color.Gray) },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = PrimaryBlue
                            )
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("CONFIRM PASSWORD", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            placeholder = { Text("Repeat", fontSize = 14.sp) },
                            leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null, tint = Color.Gray) },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = PrimaryBlue
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onSignUpSuccess,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkNavy)
                ) {
                    Text("Generate Customer ID", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Already registered?", fontSize = 12.sp, color = Color.Gray)
                    TextButton(onClick = onNavigateToLogin) {
                        Text("Sign In to NetBanking", fontSize = 12.sp, color = PrimaryBlue, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}
