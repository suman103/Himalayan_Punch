package com.example.himalayanpunch.utils

import androidx.compose.runtime.mutableStateListOf
import com.example.himalayanpunch.model.CafeOrder

// ── Trip / Order Manager ──────────────────────────────────────────────────────
// Manages in-memory state for the current user's active trip/orders.

object TripManager {
    val activeOrders = mutableStateListOf<CafeOrder>()

    fun addOrder(order: CafeOrder) {
        activeOrders.add(order)
    }

    fun removeOrder(orderId: String) {
        activeOrders.removeAll { it.orderId == orderId }
    }

    fun clearAll() {
        activeOrders.clear()
    }
}