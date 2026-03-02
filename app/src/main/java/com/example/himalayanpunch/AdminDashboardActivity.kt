package com.example.himalayanpunch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.ui.theme.CafeType
import com.example.himalayanpunch.view.admin.AdminBookingsScreen
import com.example.himalayanpunch.view.admin.AdminManagePlacesScreen
import com.example.himalayanpunch.view.admin.AdminStatsScreen
import com.example.himalayanpunch.view.ProfileScreen
import com.example.himalayanpunch.viewmodel.BookingViewModel
import com.example.himalayanpunch.viewmodel.MenuViewModel

class AdminDashboardActivity : ComponentActivity() {
    private val menuViewModel: MenuViewModel by viewModels()
    private val orderViewModel: BookingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdminDashboard(menuViewModel = menuViewModel, orderViewModel = orderViewModel)
        }
    }
}

@Composable
fun AdminDashboard(menuViewModel: MenuViewModel, orderViewModel: BookingViewModel) {
    var selectedTab by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        menuViewModel.loadItems()
        orderViewModel.loadAllOrders()
    }

    Scaffold(
        containerColor = CafeColors.Espresso,
        bottomBar = {
            Surface(modifier = Modifier.fillMaxWidth(), color = CafeColors.Roast, tonalElevation = 0.dp) {
                Column {
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(CafeColors.Divider))
                    Row(
                        modifier = Modifier.fillMaxWidth().navigationBarsPadding()
                            .padding(horizontal = 8.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        listOf("📊 Overview", "☕ Menu", "🧾 Orders", "👤 Account")
                            .forEachIndexed { i, label ->
                                val sel = selectedTab == i
                                Surface(
                                    onClick = { selectedTab = i },
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (sel) CafeColors.AmberDim else Color.Transparent
                                ) {
                                    Text(
                                        label,
                                        style = CafeType.labelSm.copy(
                                            color = if (sel) CafeColors.Amber else CafeColors.TextMuted,
                                            fontWeight = if (sel) FontWeight.Bold else FontWeight.Normal
                                        ),
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                    )
                                }
                            }
                    }
                }
            }
        }
    ) { padding ->
        when (selectedTab) {
            0 -> AdminStatsScreen(menuViewModel, orderViewModel, padding)
            1 -> AdminManagePlacesScreen(menuViewModel, padding)
            2 -> AdminBookingsScreen(orderViewModel, padding)
            3 -> ProfileScreen(padding)
        }
    }
}