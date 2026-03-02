package com.example.himalayanpunch.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.himalayanpunch.model.CafeOrder
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.ui.theme.CafeType
import com.example.himalayanpunch.viewmodel.BookingViewModel

@Composable
fun MyBookingScreen(
    padding: PaddingValues = PaddingValues(),
    bookingViewModel: BookingViewModel = viewModel()
) {
    val orders = bookingViewModel.userOrders
    val ctx = LocalContext.current

    LaunchedEffect(Unit) { bookingViewModel.loadUserOrders() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CafeColors.Espresso)
            .padding(padding)
    ) {
        CafeScreenHeader(
            title = "My Orders",
            subtitle = "${orders.size} order${if (orders.size != 1) "s" else ""}"
        )

        if (orders.isEmpty()) {
            CafeEmptyState(
                icon = "🧾",
                title = "No orders yet",
                subtitle = "Place an order from the menu to see it here"
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 22.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(orders) { order ->
                    OrderCard(
                        order = order,
                        onCancel = {
                            bookingViewModel.updateStatus(order.orderId, "Cancelled") { _, msg ->
                                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun OrderCard(order: CafeOrder, onCancel: () -> Unit) {
    val (statusColor, statusBg, statusIcon) = when (order.status) {
        "Preparing"  -> Triple(CafeColors.Amber,  CafeColors.AmberDim,  "🔥")
        "Ready"      -> Triple(CafeColors.Sage,   CafeColors.SageDim,   "✅")
        "Delivered"  -> Triple(CafeColors.Sage,   CafeColors.SageDim,   "🎉")
        "Cancelled"  -> Triple(CafeColors.Red,    CafeColors.RedDim,    "❌")
        else         -> Triple(Color(0xFFD4841A),  Color(0xFF3D2506),    "⏳")
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
                Box(modifier = Modifier.fillMaxSize().background(
                    Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(0.55f)), startY = 30f)
                ))
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
                    style = CafeType.headingMd.copy(color = Color.White),
                    modifier = Modifier.align(Alignment.BottomStart).padding(12.dp)
                )
            }

            Column(modifier = Modifier.padding(14.dp)) {
                OrderDetailRow("Size", order.size)
                OrderDetailRow("Qty", order.quantity.toString())
                OrderDetailRow("Type", order.orderType)
                if (order.customization.isNotEmpty()) OrderDetailRow("Custom", order.customization)

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = CafeColors.Divider)
                Spacer(modifier = Modifier.height(10.dp))

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

@Composable
fun OrderDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = CafeType.bodyMd.copy(color = CafeColors.TextSecondary))
        Text(value, style = CafeType.titleMd.copy(color = CafeColors.TextPrimary))
    }
}