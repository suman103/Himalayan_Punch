package com.example.himalayanpunch

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.himalayanpunch.MainActivity
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.ui.theme.CafeType
import com.example.himalayanpunch.view.CafeField
import com.example.himalayanpunch.viewmodel.AuthViewModel

class RegistrationActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { RegisterScreen(authViewModel) }
    }
}

@Composable
fun RegisterScreen(viewModel: AuthViewModel = AuthViewModel()) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val ctx = LocalContext.current
    val scroll = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(CafeColors.Espresso, CafeColors.Roast)))) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(scroll).padding(horizontal = 28.dp)) {
            Spacer(modifier = Modifier.height(60.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { (ctx as? RegistrationActivity)?.finish() }) {
                    Text("←", style = TextStyle(fontSize = 22.sp, color = CafeColors.TextSecondary, fontWeight = FontWeight.Bold))
                }
                Column {
                    Text("Join Us", style = CafeType.headingLg.copy(color = CafeColors.TextPrimary))
                    Text("Create your Himalayan Punch account", style = CafeType.bodyMd.copy(color = CafeColors.TextSecondary))
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), color = CafeColors.Roast, tonalElevation = 0.dp) {
                Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    CafeField("Full Name", fullName, { fullName = it }, "Your name", "👤")
                    CafeField("Email", email, { email = it }, "your@email.com", "✉️", keyboardType = KeyboardType.Email)
                    CafeField("Password", password, { password = it }, "Min. 8 characters", "🔒",
                        isPassword = true, passwordVisible = passwordVisible, onTogglePassword = { passwordVisible = !passwordVisible })
                    CafeField("Confirm Password", confirmPassword, { confirmPassword = it }, "Repeat password", "🔒",
                        isPassword = true, passwordVisible = passwordVisible, onTogglePassword = { passwordVisible = !passwordVisible })

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        onClick = {
                            when {
                                fullName.isBlank() -> Toast.makeText(ctx, "Enter your name", Toast.LENGTH_SHORT).show()
                                email.isBlank() -> Toast.makeText(ctx, "Enter email", Toast.LENGTH_SHORT).show()
                                password.length < 8 -> Toast.makeText(ctx, "Password must be 8+ characters", Toast.LENGTH_SHORT).show()
                                password != confirmPassword -> Toast.makeText(ctx, "Passwords don't match", Toast.LENGTH_SHORT).show()
                                else -> {
                                    isLoading = true
                                    viewModel.register(fullName.trim(), email.trim(), password) { success, msg ->
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
                        if (isLoading) CircularProgressIndicator(Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                        else Text("Create Account", style = CafeType.titleLg.copy(color = Color.White, fontWeight = FontWeight.ExtraBold))
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}