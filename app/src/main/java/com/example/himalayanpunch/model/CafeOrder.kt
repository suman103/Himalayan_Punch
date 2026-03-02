package com.example.himalayanpunch.model

// ── Order model ───────────────────────────────────────────────────────────────
data class CafeOrder(
    val orderId: String = "",
    val userId: String = "",
    val userEmail: String = "",
    val userName: String = "",
    val itemId: String = "",
    val itemName: String = "",
    val itemCategory: String = "",
    val itemImageUrl: String = "",
    val itemPrice: String = "",
    val quantity: Int = 1,
    val size: String = "Medium",
    val customization: String = "",
    val customerName: String = "",
    val customerPhone: String = "",
    val deliveryAddress: String = "",
    val orderType: String = "Dine In",  // "Dine In", "Takeaway", "Delivery"
    val specialNote: String = "",
    val status: String = "Pending",     // Pending, Preparing, Ready, Delivered, Cancelled
    val orderedAt: Long = 0L,
    val totalPrice: String = ""
)