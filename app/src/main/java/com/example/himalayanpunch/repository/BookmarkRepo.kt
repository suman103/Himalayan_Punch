package com.example.himalayanpunch.repository

import com.example.himalayanpunch.model.CafeOrder

// ── Order Repository ──────────────────────────────────────────────────────────

interface BookmarkRepo {
    fun createOrder(order: CafeOrder, onResult: (Boolean, String) -> Unit)
    fun getUserOrders(userId: String, onResult: (Boolean, String, List<CafeOrder>) -> Unit)
    fun getAllOrders(onResult: (Boolean, String, List<CafeOrder>) -> Unit)
    fun updateOrderStatus(orderId: String, status: String, onResult: (Boolean, String) -> Unit)
}