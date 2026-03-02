package com.example.himalayanpunch.view.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.ui.theme.CafeType
import com.example.himalayanpunch.utils.MockData
import com.example.himalayanpunch.view.CafeEmptyState
import com.example.himalayanpunch.viewmodel.BookingViewModel
import com.example.himalayanpunch.viewmodel.MenuViewModel

@Composable
fun AdminStatsScreen(
    menuViewModel: MenuViewModel,
    orderViewModel: BookingViewModel,
    padding: PaddingValues
) {
    val menuItems = menuViewModel.items.ifEmpty { MockData.menuItems }
    val orders = orderViewModel.allOrders

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(CafeColors.Espresso).padding(padding),
        contentPadding = PaddingValues(bottom = 28.dp)
    ) {
        item {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(CafeColors.Roast, CafeColors.Espresso)))
            ) {
                Box(
                    modifier = Modifier.size(220.dp).align(Alignment.TopEnd).offset(40.dp, (-30).dp)
                        .background(
                            Brush.radialGradient(listOf(CafeColors.Amber.copy(0.1f), Color.Transparent)),
                            CircleShape
                        )
                )
                Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 52.dp, bottom = 24.dp)) {
                    Text("☕ ADMIN", style = CafeType.labelSm.copy(color = CafeColors.Amber))
                    Text("HimalayanPunch", style = CafeType.displayLg.copy(color = CafeColors.TextPrimary))
                    Text("Cafe Management Panel", style = CafeType.bodyMd.copy(color = CafeColors.TextSecondary))
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AdminStatCard(Modifier.weight(1f), "☕", "Menu Items", "${menuItems.size}", CafeColors.Amber)
                AdminStatCard(Modifier.weight(1f), "🧾", "Total Orders", "${orders.size}", Color(0xFF5B8FA8))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AdminStatCard(Modifier.weight(1f), "⏳", "Pending", "${orders.count { it.status == "Pending" }}", CafeColors.Yellow)
                AdminStatCard(Modifier.weight(1f), "✅", "Delivered", "${orders.count { it.status == "Delivered" }}", CafeColors.Sage)
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.width(4.dp).height(18.dp).background(CafeColors.Amber, RoundedCornerShape(2.dp)))
                Spacer(modifier = Modifier.width(10.dp))
                Text("Recent Orders", style = CafeType.headingMd.copy(color = CafeColors.TextPrimary))
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        if (orders.isEmpty()) {
            item { CafeEmptyState("🧾", "No orders yet", "Customer orders will appear here") }
        } else {
            items(orders.take(5)) { order ->
                Box(modifier = Modifier.padding(horizontal = 22.dp, vertical = 4.dp)) {
                    AdminOrderRow(order = order, onConfirm = {}, onReject = {}, onReady = {})
                }
            }
        }
    }
}

@Composable
fun AdminStatCard(modifier: Modifier, icon: String, label: String, value: String, color: Color) {
    Surface(modifier = modifier, shape = RoundedCornerShape(18.dp), color = CafeColors.Roast, tonalElevation = 0.dp) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(icon, style = TextStyle(fontSize = 24.sp))
                Box(modifier = Modifier.size(8.dp).background(color, CircleShape))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(value, style = CafeType.displayLg.copy(color = CafeColors.TextPrimary, fontSize = 28.sp))
            Text(label, style = CafeType.bodyMd.copy(color = CafeColors.TextSecondary))
        }
    }
}