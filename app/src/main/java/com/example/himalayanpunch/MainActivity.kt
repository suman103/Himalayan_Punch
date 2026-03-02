package com.example.himalayanpunch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.view.*
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val userName = intent.getStringExtra("userName")
            ?: FirebaseAuth.getInstance().currentUser?.displayName
            ?: "Guest"
        setContent { MainScreen(userName = userName) }
    }
}

@Composable
fun MainScreen(userName: String = "Guest") {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = CafeColors.Espresso,
        bottomBar = {
            CafeBottomNav(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
        }
    ) { padding ->
        when (selectedTab) {
            0 -> DashboardScreen(padding = padding, userName = userName)
            1 -> PlacesScreen(padding = padding)
            2 -> BookmarkScreen(padding = padding)
            3 -> MyBookingScreen(padding = padding)
            4 -> ProfileScreen(padding = padding)
        }
    }
}

@Composable
fun CafeBottomNav(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    data class NavItem(val label: String, val icon: String, val index: Int)

    val items = listOf(
        NavItem("Home",    "🏠", 0),
        NavItem("Menu",    "☕", 1),
        NavItem("Saved",   "❤️", 2),
        NavItem("Orders",  "🧾", 3),
        NavItem("Profile", "👤", 4)
    )

    Surface(
        modifier       = Modifier.fillMaxWidth(),
        color          = CafeColors.Roast,
        tonalElevation = 0.dp
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(CafeColors.Divider)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 6.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                items.forEach { item ->
                    val selected = selectedTab == item.index
                    val labelColor by animateColorAsState(
                        targetValue   = if (selected) CafeColors.Amber else CafeColors.TextMuted,
                        animationSpec = tween(200),
                        label         = ""
                    )

                    // ✅ FIX: clickable on the Column so the entire
                    //    tab area (icon + label) responds to taps.
                    //    ripple is disabled for a cleaner look but
                    //    you can remove interactionSource/indication
                    //    to get the default ripple back.
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication        = null   // remove for default ripple
                            ) { onTabSelected(item.index) },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(
                                    if (selected) CafeColors.AmberDim else Color.Transparent,
                                    RoundedCornerShape(14.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                item.icon,
                                style = TextStyle(fontSize = if (selected) 20.sp else 18.sp)
                            )
                        }
                        Text(
                            item.label,
                            style = TextStyle(
                                fontSize   = 9.sp,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                color      = labelColor
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Box(
                            modifier = Modifier
                                .size(if (selected) 4.dp else 0.dp)
                                .background(CafeColors.Amber, CircleShape)
                        )
                    }
                }
            }
        }
    }
}