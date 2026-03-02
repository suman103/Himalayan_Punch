package com.example.himalayanpunch.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.himalayanpunch.ui.theme.CafeColors
import com.example.himalayanpunch.ui.theme.CafeType

data class AppNotification(
    val id: String = "",
    val title: String = "",
    val message: String = "",
    val icon: String = "🔔",
    val timestamp: String = "",
    val isRead: Boolean = false
)

@Composable
fun NotificationScreen(padding: PaddingValues = PaddingValues()) {
    val notifications = remember {
        listOf(
            AppNotification("1", "Order Ready!", "Your Himalayan Punch Espresso is ready for pickup.", "✅", "2 min ago"),
            AppNotification("2", "Special Offer", "Get 20% off on all cold beverages today!", "🎉", "1 hr ago"),
            AppNotification("3", "New Item", "Try our new Yak Butter Latte — now available!", "☕", "Yesterday"),
            AppNotification("4", "Order Placed", "Your order has been received and is being prepared.", "🧾", "2 days ago", isRead = true)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CafeColors.Espresso)
            .padding(padding)
    ) {
        CafeScreenHeader(title = "Notifications", subtitle = "${notifications.count { !it.isRead }} unread")

        if (notifications.isEmpty()) {
            CafeEmptyState("🔔", "No notifications", "You're all caught up!")
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 22.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(notifications.size) { i ->
                    NotificationCard(notifications[i])
                }
            }
        }
    }
}

@Composable
fun NotificationCard(notification: AppNotification) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = if (notification.isRead) CafeColors.Roast else CafeColors.Mocha,
        tonalElevation = 0.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(14.dp)) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = CafeColors.AmberDim,
                modifier = Modifier.size(44.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(notification.icon, style = TextStyle(fontSize = 18.sp))
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(notification.title, style = CafeType.titleMd.copy(color = CafeColors.TextPrimary))
                    Text(notification.timestamp, style = CafeType.labelSm.copy(color = CafeColors.TextMuted))
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(notification.message, style = CafeType.bodyMd.copy(color = CafeColors.TextSecondary))
            }
        }
    }
}