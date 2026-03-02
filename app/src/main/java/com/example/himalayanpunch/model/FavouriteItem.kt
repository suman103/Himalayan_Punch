package com.example.himalayanpunch.model

// ── Wishlist / Favourites item ─────────────────────────────────────────────────
data class FavouriteItem(
    val itemId: String = "",
    val name: String = "",
    val category: String = "",
    val price: String = "",
    val imageUrl: String = "",
    val rating: Float = 4.0f,
    val isVeg: Boolean = true,
    val tags: List<String> = listOf()
)