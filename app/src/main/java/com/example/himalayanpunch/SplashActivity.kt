package com.example.himalayanpunch

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.himalayanpunch.MainActivity
import com.example.himalayanpunch.ui.theme.CafeColors
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    private val ADMIN_EMAIL = "admin@himalayan.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SplashScreen(
                onNavigate = { isLoggedIn, name, isAdmin ->
                    when {
                        isLoggedIn && isAdmin -> startActivity(Intent(this, AdminDashboardActivity::class.java))
                        isLoggedIn -> startActivity(Intent(this, MainActivity::class.java).apply { putExtra("userName", name) })
                        else -> startActivity(Intent(this, LoginActivity::class.java))
                    }
                    finish()
                },
                adminEmail = ADMIN_EMAIL
            )
        }
    }
}

@Composable
fun SplashScreen(onNavigate: (Boolean, String, Boolean) -> Unit, adminEmail: String) {
    val alpha by animateFloatAsState(targetValue = 1f, animationSpec = tween(1000, easing = FastOutSlowInEasing), label = "")
    val progress by animateFloatAsState(targetValue = 1f, animationSpec = tween(1900), label = "")

    LaunchedEffect(Unit) {
        delay(2300)
        val user = FirebaseAuth.getInstance().currentUser
        onNavigate(user != null, user?.displayName ?: "Guest", user?.email == adminEmail)
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(Brush.verticalGradient(listOf(CafeColors.Espresso, CafeColors.Roast, CafeColors.Espresso))),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.size(360.dp).align(Alignment.Center)
            .background(Brush.radialGradient(listOf(CafeColors.Amber.copy(0.08f), Color.Transparent)), CircleShape))

        Column(modifier = Modifier.alpha(alpha), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(bottom = 6.dp)) {
                repeat(3) { i ->
                    val steamAlpha by animateFloatAsState(
                        targetValue = 0.6f,
                        animationSpec = infiniteRepeatable(animation = keyframes {
                            durationMillis = 1200
                            0.1f at 0; 0.6f at (i * 200 + 200); 0.1f at (i * 200 + 600)
                        }), label = ""
                    )
                    Text("~", style = TextStyle(fontSize = 16.sp, color = CafeColors.Amber.copy(steamAlpha)))
                }
            }

            Box(
                modifier = Modifier.size(90.dp)
                    .background(Brush.linearGradient(listOf(CafeColors.Amber, CafeColors.AmberLight)), RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) { Text("☕", style = TextStyle(fontSize = 44.sp)) }

            Spacer(modifier = Modifier.height(22.dp))
            Text("Himalayan", style = TextStyle(fontSize = 36.sp, fontWeight = FontWeight.Bold, color = CafeColors.TextPrimary, letterSpacing = 1.sp))
            Text("PUNCH", style = TextStyle(fontSize = 36.sp, fontWeight = FontWeight.ExtraBold, color = CafeColors.Amber, letterSpacing = 6.sp))
            Spacer(modifier = Modifier.height(8.dp))
            Text("BREW · SIP · SAVOUR", style = TextStyle(fontSize = 10.sp, color = CafeColors.TextMuted, letterSpacing = 4.sp, fontWeight = FontWeight.Medium))

            Spacer(modifier = Modifier.height(60.dp))

            Box(modifier = Modifier.width(140.dp).height(3.dp).clip(RoundedCornerShape(2.dp)).background(CafeColors.Mocha)) {
                Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(progress)
                    .background(Brush.horizontalGradient(listOf(CafeColors.Amber, CafeColors.AmberLight)), RoundedCornerShape(2.dp)))
            }
        }
    }
}