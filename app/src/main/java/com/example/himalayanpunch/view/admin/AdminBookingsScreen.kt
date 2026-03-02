package com.example.himalayanpunch.view.admin

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.himalayanpunch.model.CafeOrder
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.ui.theme.CafeType
import com.example.himalayanpunch.view.CafeEmptyState
import com.example.himalayanpunch.view.CafeScreenHeader
import com.example.himalayanpunch.viewmodel.BookingViewModel

@Composable
fun AdminBookingsScreen(viewModel: BookingViewModel, padding: PaddingValues) {
    val orders = viewModel.allOrders
    val ctx = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().background(CafeColors.Espresso).padding(padding)) {
        CafeScreenHeader(title = "All Orders", subtitle = "${orders.size} orders")

        if (orders.isEmpty()) {
            CafeEmptyState("🧾", "No orders yet", "Customer orders will show here")
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 22.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(orders) { order ->
                    AdminOrderRow(
                        order = order,
                        onConfirm = {
                            viewModel.updateStatus(order.orderId, "Preparing") { _, msg ->
                                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
                            }
                        },
                        onReady = {
                            viewModel.updateStatus(order.orderId, "Ready") { _, msg ->
                                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
                            }
                        },
                        onReject = {
                            viewModel.updateStatus(order.orderId, "Cancelled") { _, msg ->
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
fun AdminOrderRow(
    order: CafeOrder,
    onConfirm: () -> Unit,
    onReady: () -> Unit,
    onReject: () -> Unit
) {
    val (statusColor, statusBg) = when (order.status) {
        "Preparing" -> Pair(CafeColors.Amber, CafeColors.AmberDim)
        "Ready"     -> Pair(CafeColors.Sage,  CafeColors.SageDim)
        "Delivered" -> Pair(CafeColors.Sage,  CafeColors.SageDim)
        "Cancelled" -> Pair(CafeColors.Red,   CafeColors.RedDim)
        else        -> Pair(Color(0xFFD4841A), Color(0xFF3D2506))
    }

    Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = CafeColors.Roast) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(order.itemName, style = CafeType.titleLg.copy(color = CafeColors.TextPrimary))
                    Text("${order.quantity}x · ${order.size} · ${order.orderType}", style = CafeType.bodyMd.copy(color = CafeColors.TextSecondary))
                    Text(order.customerName, style = CafeType.bodyMd.copy(color = CafeColors.TextMuted))
                    Text(order.totalPrice, style = CafeType.titleMd.copy(color = CafeColors.Amber))
                }
                Surface(shape = RoundedCornerShape(6.dp), color = statusBg) {
                    Text(order.status, style = CafeType.labelSm.copy(color = statusColor, fontWeight = FontWeight.ExtraBold),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
                }
            }

            if (order.status == "Pending" || order.status == "Preparing") {
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = CafeColors.Divider)
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (order.status == "Pending") {
                        OutlinedButton(
                            onClick = onReject,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = CafeColors.Red),
                            contentPadding = PaddingValues(vertical = 8.dp)
                        ) { Text("Cancel", style = CafeType.label.copy(color = CafeColors.Red)) }
                        Button(
                            onClick = onConfirm,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = CafeColors.Amber),
                            contentPadding = PaddingValues(vertical = 8.dp),
                            elevation = ButtonDefaults.buttonElevation(0.dp)
                        ) { Text("Prepare", style = CafeType.label.copy(color = Color.White)) }
                    } else {
                        Button(
                            onClick = onReady,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = CafeColors.Sage),
                            contentPadding = PaddingValues(vertical = 8.dp),
                            elevation = ButtonDefaults.buttonElevation(0.dp)
                        ) { Text("Mark Ready ✓", style = CafeType.label.copy(color = Color.White)) }
                    }
                }
            }
        }
    }
}