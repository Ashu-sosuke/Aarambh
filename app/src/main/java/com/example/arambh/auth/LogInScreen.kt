package com.example.arambh.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.arambh.R
import com.example.arambh.Screen

@Composable
fun LogInScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {

    var isLoading by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val neonGreen = Color(0xFF00FF88)
    val darkBg = Color(0xFF0A0A0A)

    LaunchedEffect(Unit) {
        viewModel.initializeRememberMe(context)
    }

    val isLoggedIn by viewModel.authState.collectAsState()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn){
            navController.navigate(Screen.HomeScreen.route){
                popUpTo(0)
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBg)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.whatsapp_image_2025_09_15_at_00_28_41_82303890),
            contentDescription = "Login Image",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            "Aarambh",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = neonGreen
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text("Login to your account.", color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address", color = neonGreen) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = neonGreen,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = neonGreen,
                cursorColor = neonGreen,
                unfocusedLabelColor = Color.Gray
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = neonGreen) },
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = neonGreen,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = neonGreen,
                cursorColor = neonGreen,
                unfocusedLabelColor = Color.Gray
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = rememberMe,
                onCheckedChange = { rememberMe = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = neonGreen,
                    uncheckedColor = Color.Gray
                )
            )
            Text("Remember me", color = Color.White)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Neon Login Button
        Button(
            onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    isLoading = true
                    viewModel.login(
                        context = context,
                        email = email,
                        password = password,
                        rememberMe = rememberMe
                    ) { success, error ->
                        isLoading = false
                        if (success) {
                            navController.navigate(Screen.HomeScreen.route) {
                                popUpTo(0)
                            }
                        } else {
                            errorMessage = error
                            Toast.makeText(context, error ?: "Login failed", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    errorMessage = "Please enter email and password"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            contentPadding = PaddingValues()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(neonGreen, Color(0xFF00FFC6))
                        ),
                        shape = RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.Black,
                        strokeWidth = 3.dp,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Login", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }            }
        }

        // Show error message under button
        errorMessage?.let { msg ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = msg, color = Color.Red, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text("Don't have an account?", fontSize = 16.sp, color = Color.White)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                "Log In",
                color = neonGreen,
                fontSize = 16.sp,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.SignUpScreen.route)
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            "Forgot password?",
            modifier = Modifier.clickable { /* TODO: reset password */ },
            color = neonGreen
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Or sign in with", color = Color.Gray)
        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            Image(
                painter = painterResource(R.drawable.google),
                contentDescription = "login with google",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        // TODO: Implement Google Sign-In
                    }
            )
        }
    }
}
