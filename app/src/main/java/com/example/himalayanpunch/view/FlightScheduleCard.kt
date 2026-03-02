package com.example.himalayanpunch.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.himalayanpunch.model.CafeOrder
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.ui.theme.CafeType

// FlightScheduleCard is a reusable card component for displaying order/booking schedule info.

@Composable
fun FlightScheduleCard(
    order: CafeOrder,
    onCancel: () -> Unit = {}
) {
    val (statusColor, statusBg, statusIcon) = when (order.status) {
        "Preparing"  -> Triple(CafeColors.Amber,  CafeColors.AmberDim,  "🔥")
        "Ready"      -> Triple(CafeColors.Sage,   CafeColors.SageDim,   "✅")
        "Delivered"  -> Triple(CafeColors.Sage,   CafeColors.SageDim,   "🎉")
        "Cancelled"  -> Triple(CafeColors.Red,    CafeColors.RedDim,    "❌")
        else         -> Triple(CafeColors.Amber,  CafeColors.AmberDim,  "⏳")
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = CafeColors.Roast,
        tonalElevation = 0.dp
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().height(100.dp)) {
                if (order.itemImageUrl.isNotEmpty()) {
                    AsyncImage(
                        model = order.itemImageUrl,
                        contentDescription = order.itemName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .background(Brush.linearGradient(listOf(CafeColors.Mocha, CafeColors.Latte))),
                        contentAlignment = Alignment.Center
                    ) { Text("☕", style = TextStyle(fontSize = 36.sp)) }
                }
                Surface(
                    modifier = Modifier.align(Alignment.TopEnd).padding(10.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = statusBg
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(statusIcon, style = TextStyle(fontSize = 10.sp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(order.status, style = CafeType.label.copy(color = statusColor, fontWeight = FontWeight.ExtraBold))
                    }
                }
                Text(
                    order.itemName,
                    style = CafeType.headingMd.copy(color = androidx.compose.ui.graphics.Color.White),
                    modifier = Modifier.align(Alignment.BottomStart).padding(12.dp)
                )
            }

            Column(modifier = Modifier.padding(14.dp)) {
                OrderDetailRow("Size", order.size)
                OrderDetailRow("Qty", order.quantity.toString())
                OrderDetailRow("Type", order.orderType)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(order.totalPrice, style = CafeType.price.copy(color = CafeColors.Amber))
                    if (order.status == "Pending") {
                        OutlinedButton(
                            onClick = onCancel,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = CafeColors.Red),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text("Cancel", style = CafeType.label.copy(color = CafeColors.Red))
                        }
                    }
                }
            }
        }
    }
}