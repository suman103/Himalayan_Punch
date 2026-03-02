package com.example.himalayanpunch.model

// ── Menu Item model ───────────────────────────────────────────────────────────
data class MenuItem(
    val itemId: String = "",
    val name: String = "",
    val category: String = "",      // "Hot Coffee", "Cold Coffee", "Tea", "Food", "Specials"
    val subCategory: String = "",   // Espresso, Latte, Frappe, etc.
    val price: String = "",
    val priceValue: Long = 0L,
    val imageUrl: String = "",
    val description: String = "",
    val rating: Float = 4.0f,
    val isFeatured: Boolean = false,
    val isAvailable: Boolean = true,
    val isVeg: Boolean = true,
    val calories: String = "",
    val prepTime: String = "",      // "5 min", "10 min"
    val sizes: List<String> = listOf("Small", "Medium", "Large"),
    val customizations: List<String> = listOf(),  // "Extra Shot", "Oat Milk", etc.
    val tags: List<String> = listOf()             // "Bestseller", "New", "Spicy"
)