package com.example.himalayanpunch.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.himalayanpunch.model.CafeOrder
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.ui.theme.CafeType

// SchedulesSection — displays a compact horizontal list of active orders/schedules.

@Composable
fun SchedulesSection(
    padding: PaddingValues = PaddingValues(),
    orders: List<CafeOrder>
) {
    if (orders.isEmpty()) return

    Column(modifier = Modifier.fillMaxWidth().padding(padding)) {
        CafeSectionHeader(title = "Active Orders", badge = "${orders.size}")
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(orders) { order ->
                ScheduleChip(order)
            }
        }
    }
}

@Composable
fun ScheduleChip(order: CafeOrder) {
    val (statusColor, statusBg) = when (order.status) {
        "Preparing" -> Pair(CafeColors.Amber, CafeColors.AmberDim)
        "Ready"     -> Pair(CafeColors.Sage, CafeColors.SageDim)
        "Cancelled" -> Pair(CafeColors.Red, CafeColors.RedDim)
        else        -> Pair(CafeColors.Amber, CafeColors.AmberDim)
    }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = CafeColors.Roast,
        modifier = Modifier.width(180.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(order.itemName, style = CafeType.titleMd.copy(color = CafeColors.TextPrimary), maxLines = 1)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(shape = RoundedCornerShape(6.dp), color = statusBg) {
                    Text(order.status, style = CafeType.labelSm.copy(color = statusColor, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                }
            }
            Text(order.totalPrice, style = CafeType.bodyMd.copy(color = CafeColors.Amber))
        }
    }
}