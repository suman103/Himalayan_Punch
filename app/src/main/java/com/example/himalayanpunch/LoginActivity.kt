package com.example.himalayanpunch

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.himalayanpunch.MainActivity
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.ui.theme.CafeType
import com.example.himalayanpunch.view.CafeField
import com.example.himalayanpunch.viewmodel.AuthViewModel

class LoginActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { LoginScreen(authViewModel) }
    }
}

@Composable
fun LoginScreen(viewModel: AuthViewModel = AuthViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val ctx = LocalContext.current
    val scroll = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize()
            .background(Brush.verticalGradient(listOf(CafeColors.Espresso, CafeColors.Roast)))
    ) {
        Box(modifier = Modifier.size(320.dp).align(Alignment.TopCenter).offset(y = (-80).dp)
            .background(Brush.radialGradient(listOf(CafeColors.Amber.copy(0.12f), Color.Transparent)), CircleShape))

        Column(modifier = Modifier.fillMaxSize().verticalScroll(scroll).padding(horizontal = 28.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(80.dp))

            Text("☕", style = TextStyle(fontSize = 48.sp))
            Spacer(modifier = Modifier.height(10.dp))
            Text("Welcome Back", style = CafeType.displayLg.copy(color = CafeColors.TextPrimary))
            Text(text = "Sign in to your Himalayan Punch account", style = CafeType.bodyMd.copy(color = CafeColors.TextSecondary), textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(40.dp))

            Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), color = CafeColors.Roast, tonalElevation = 0.dp) {
                Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    CafeField("Email", email, { email = it }, "your@email.com", "✉️", keyboardType = KeyboardType.Email)
                    CafeField("Password", password, { password = it }, "••••••••", "🔒",
                        isPassword = true, passwordVisible = passwordVisible, onTogglePassword = { passwordVisible = !passwordVisible })

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        onClick = {
                            when {
                                email.isBlank() -> Toast.makeText(ctx, "Enter your email", Toast.LENGTH_SHORT).show()
                                password.isBlank() -> Toast.makeText(ctx, "Enter your password", Toast.LENGTH_SHORT).show()
                                else -> {
                                    isLoading = true
                                    viewModel.login(email.trim(), password) { success, msg ->
                                        isLoading = false
                                        if (success) {
                                            ctx.startActivity(Intent(ctx, MainActivity::class.java).apply {
                                                putExtra("userName", msg)
                                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            })
                                        } else Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CafeColors.Amber),
                        enabled = !isLoading,
                        elevation = ButtonDefaults.buttonElevation(0.dp)
                    ) {
                        if (isLoading) CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                        else Text("Sign In", style = CafeType.titleLg.copy(color = Color.White, fontWeight = FontWeight.ExtraBold))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = CafeColors.TextSecondary, fontSize = 14.sp)) { append("New here? ") }
                    withStyle(SpanStyle(color = CafeColors.Amber, fontWeight = FontWeight.Bold, fontSize = 14.sp)) { append("Create an account") }
                },
                modifier = Modifier.clickable { ctx.startActivity(Intent(ctx, RegistrationActivity::class.java)) },
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}